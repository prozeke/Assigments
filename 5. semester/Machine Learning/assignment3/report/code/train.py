import numpy as np
import random as rd
import sys
import scipy.io

rate = 0.0005	
error = 0
epoch = 400
batchSize = 30
trainFile =  sys.argv[1]

def softmax(vector):
	for i in range(len(vector)):
		vector[i] = np.exp(vector[i])
	divisor = vector.sum()
	if(np.isnan(divisor) or np.isinf(divisor)):
		print("disisor is nan in softmax")
		print(divisor)
		quit()
	for i in range(len(vector)):
		vector[i] = vector[i] / divisor
	return vector
 
def crossEntropyError(vector, y):
	error = 0
	error = -(np.log(vector[y]) - np.log(vector.sum()))
	return error
	
def softmaxDelta(vector, y):
#	ret = [0 for i in vector]
	for i in range(len(vector)):
		if i == y:
			vector[i] += vector[i] - 1
	return vector

class Sigmoid:
	def __init__(self):
		return
	
	def activate(self, x):
		return 1.0/(1 + np.exp(-x)) 		
	
	def derivative(self,x):
		return  x * (1 - x)

class Tanh:
	def __init__(self):
		return 
	
	def activate(self, x):
		val1 = np.exp(x)
		val2 = np.exp(-x)
		return (val1 - val2) / (val1 + val2)

	def derivative(self, x):
		return 1-x**2
		
class ReLu:
	def __init__(self):
		return 
	
	def activate(self, x):
		if(x > 0):
			return x
		else:
			return 0
			
	def derivative(self, x):
		if(x > 0):
			return 1
		else:
			return 0
			
class NoActivation:
	def __init__(self):
		return 
	
	def activate(self, x):
		return x
			
	def derivative(self, x):
		return 1

class Layer:
		# weightNum is the number of weights each layer has
		# neuronNum is neuron number
		# func is layers activation function class
		# bias is the bias vector of this layer   
		# inpV is the input vector for that layer
		def __init__(self, weightNum, neuronNum, func):
			self.bias = np.random.randn(neuronNum) * 0.01
			self.func = func
			self.inpV = None
			self.weights = np.random.randn(neuronNum, weightNum) * 0.01
			return	
		
		# returns the input of the next layer
		def forward(self):
			ret = np.zeros(len(self.weights))
			for neuron in range(len(self.weights)):
				z = np.dot(self.weights[neuron], self.inpV) + self.bias[neuron]
				ret[neuron] = self.func.activate(z)
			return ret
		
		# inpV are the input vector of the layer after this layer.
		# Finds the derivative of the layer.
		# Makes activations the derivative vector where activasions[i] is the derivative of the
		# i'th neurons activasion function with respect to input of that activation function. 	
		def layerDerivative(self, inpV):
			ret = np.array(inpV)
			for i in range(len(inpV)):
				ret[i] = self.func.derivative(inpV[i])
			return ret
		
		# inpV are the input vector of the layer after this layer.	
		# weightBefore is weights before this level
		# lambdaBefore is the lambda for
		# returns delta vector for that class 
		def findDelta(self, inpV, weightBefore, deltaBefore):
			der = self.layerDerivative(inpV)
			delta = np.dot(weightBefore.transpose(),deltaBefore)
			delta = np.multiply(delta, der)
			return delta
			
			
class NeuralNetwork:
	# NeuronNumV[i] is the number of neurons in i'th layer
	# FunctionV[i] is the activasion function in i'th layer
	# FunctionV elements can be "tanh", "sigmoid", "relu"
	# Default is a neuron with no activasion function
	# ClassNum is the number of output classes
	# If neuronNumV and functionV is an empty listi then it will create a single layer network.
	def __init__(self, neuronNumV, functionV, classNum, featureNum):
		relu = ReLu()
		sigmoid = Sigmoid()
		tanh = Tanh()
		default = NoActivation()
		self.layers = []
		fdict = {"relu": relu, "sigmoid": sigmoid, "tanh": tanh}
		if(len(neuronNumV) != len( functionV)):
			print("Each layer function must be specified")
			print("Error in NeuralNetwork class while initializing object")
			quit()
		for i in range(len(functionV)):
			func = fdict.get(functionV[i])
			if(func):
				functionV[i] = func
			else:
				functionV[i] = default
		if(len(neuronNumV) > 0):
			self.layers.append(Layer(featureNum, neuronNumV[0],functionV[0]))
			for i in range(1, len(neuronNumV)):
		 		self.layers.append(Layer(neuronNumV[i-1], neuronNumV[i],functionV[i]))
			self.layers.append(Layer(neuronNumV[-1], classNum, default))	# Last layer. Input of the cost function.
		else:
			self.layers.append(Layer(featureNum, classNum, default))	# Single layer network
	# feauters is the feature is a feature vector. It must have the same size of the featureNum above
	# y is the class number
	# feedforwards data		
	# returns last layers outputs
	def feedForward(self, features, y):
		self.layers[0].inpV = features
		for i in range(1,len(self.layers)):
			self.layers[i].inpV = self.layers[i-1].forward()
		return self.layers[-1].forward()
		
	# Backpropagates
	# Returns deltas. This deltas will be used for weight updating
	def backProp(self, vector, y):
		global error
		wder= []
		bder = []
		for layer in self.layers:
			wder.append(np.zeros(layer.weights.shape)) ## each neuron has its own lamda value
			bder.append(np.zeros(len(layer.bias.shape)))
		#print(vector)
		vector = softmax(vector)
		#print(vector)
		#quit()
		error = crossEntropyError(vector, y) + error
		#print( crossEntropyError(vector, y))
		delta = softmaxDelta(vector, y)
		wder[-1] = np.dot(self.layers[-1].weights.transpose(), delta)
		bder[-1] = delta	
		length = len(self.layers) - 1
		for i in range(length):
			cur = length - i - 1
			layer = self.layers[cur]
			inpBefore = self.layers[cur + 1].inpV
			weightBefore = self.layers[cur + 1].weights
			delta = layer.findDelta(inpBefore, weightBefore, delta)
			wder[cur] =  np.dot(self.layers[cur].weights.transpose(), delta)
			bder[cur] = delta
		return (wder, bder)
		
	# miniBatch is a tuple list corrosponds of input feautures and correct classes
	# return error for that batch
	def miniBatchUpdate(self, minibatch): 
		global rate
		wder = []
		bder = []
		for layer in self.layers:
			wder.append(np.zeros(layer.weights.shape)) ## each neuron has its own lamda value
			bder.append(np.zeros(layer.bias.shape))
		for inp in minibatch:
			feature = inp[0]
			y = inp[1]
			lastAct= self.feedForward(feature, y)
		#	print(lastAct); print("*****")
			(wderPass,bderPass) = self.backProp(lastAct, y)
			wder = [dw + dwp for dw,dwp in zip(wder, wderPass)]
			bder = [bw + bwp for bw, bwp in zip(bder, bderPass)]			
		wder = [layer/len(wder) for layer in wder]
		bder = [layer/len(bder) for layer in bder] 
		for i in range(len(self.layers)):
			self.layers[i].weights = self.layers[i].weights - rate*wder[i]
			self.layers[i].bias = self.layers[i].bias - rate*bder[i]
			
	def train(self, inp, miniBatchSize, epoch):
		global error
		for e in range(epoch):
			error = 0
			rd.shuffle(inp)
			miniBatches = [inp[k:k+miniBatchSize] for k in range(0,len(inp), miniBatchSize)]
			for batch in miniBatches:
				self.miniBatchUpdate(batch)
			error = error / len(inp)
			print("epoch is " + str(e) + "\terror is " + str(error))
	
# returns a list of tuples with two elements
# first element is the feauture vector and second element is the class of that feauture
def getData(dataFile):
		mat = scipy.io.loadmat(dataFile)
		ret = []
		for i in range(len(mat["x"])):
			inp = (mat["x"][i],mat["y"][0][i])
			ret.append(inp)		
		return ret
			
######## MAIN FUNCTION STARTS ########

trainList = getData(trainFile)
neuralNumV = [1]
functionV = ["relu","relu","relu"]
classNum = 5
featureNum = len(trainList[0][0])
#network = NeuralNetwork([4,5,1], functionV, classNum, featureNum)
network = NeuralNetwork([], [], classNum, featureNum)
network.train(trainList, batchSize, epoch)




			
