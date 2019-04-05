
/**
 * Gerardo Ayala, 2017.
 *
 * Based on the version by Jeff Heaton
 * (http://www.jeffheaton.com) 1-2002
 * http://www2.sys-con.com/ITSG/virtualcd/Java/archives/0705/heaton/index.html
 * Java Neural Network Example
 * Handwriting Recognition
 * -------------------------------------------------
 */


public class KohonenNetwork extends NeuralNetwork
{


    /**
     * The learning method.
     * (0 = additive, 1 = subtractive)
     */
    final int learningMethod = 1;

    /**
     * The learning rate.
     */
    final double learningRate = 0.3;

    /**
     * Abort if error is beyond this
     */
    final double errorQuitThreshold = 0.1;

    /**
     * How many retries before quit.
     */
    final int retriesBeforeQuit = 10000;

    /**
     * Reduction factor.
     */
    final double reductionFactor = 0.99;

    /**
     * The owner object, to report to.
     */
    protected Controller controller;
    //protected MainEntry owner;

    public boolean hasToAbortLearning;





    /**
     * The constructor.
     *
     * @param theNumberOfInputUnits
     * @param theNumberOfNeurons
     * @param aController
     */
    public KohonenNetwork(int theNumberOfInputUnits,int theNumberOfNeurons,
                          Controller aController)
    {
        meanSquareError = 1.0 ;
        numberOfInputUnits = theNumberOfInputUnits;
        numberOfNeurons = theNumberOfNeurons;
        connectionsStrengths = new double[numberOfNeurons][numberOfInputUnits+1];
        neuronActivationValues = new double[numberOfNeurons];
        controller = aController;
        hasToAbortLearning = false;
    }//end constructor






    public void initialize()
    {
        int i ;
        double connectionsVector[];
        //
        clearConnectionsStrengths() ;
        initializeConnectionStrengths();

        i = 0;
        while(i < numberOfNeurons)
        {
            connectionsVector = connectionsStrengths[i];
            normalizeWeights(connectionsVector);
            i = i + 1;
        }//end while
    }//end initialize




    /**
     * Set the training set to use.
     *
     * @param aTrainingSet
     */
    public void setTrainingSet(TrainingSet aTrainingSet)
    {
        trainingSet = aTrainingSet;
    }//end setTrainingSet




    /**
     * Copy the weights from this network to another.
     *
     * @param destination
     * @param source
     */
    public static void copyConnectionsStrengths(KohonenNetwork destination,
                                                KohonenNetwork source)
    {
        int i;
        //
        i = 0;
        while(i < source.connectionsStrengths.length)
        {
            System.arraycopy(source.connectionsStrengths[i],0,
                    destination.connectionsStrengths[i],0, source.connectionsStrengths[i].length);
            i = i + 1;
        }//end while

    }//end copyWeights






    /**
     * Normalize the input.
     *
     * @param inputPattern
     * @param normalizationFactor
     */
    // obtains the normalizationFactor based on the given inputPattern
    // and initializes the syntheticLastInput with 0.0
    public double[] getNormalizationFactor(final double inputPattern[],
                                           double normalizationFactor[])
    {
        double length;
        //
        length = FunctionLibrary.vectorLength(inputPattern);
        // just in case it gets too small
        if ( length < 1.E-30 )
            length = 1.E-30;
        //end if
        // The normalization factor is
        // the reciprocal of the square root of the vector length
        normalizationFactor[0] = 1.0 / Math.sqrt(length);
        return normalizationFactor;
    }//end getNormalizationFactor




    /**
     * Present an input pattern and get the
     * winning neuron.
     *
     * @param inputPattern
     * @param normalizationFactor
     * @param syntheticLastInput
     * @return The winning neuron number.
     */
    // Every node in the network is examined
    // to calculate which ones' weights are
    // most like the input vector.
    // The winning node is commonly known as the Best Matching Unit (BMU)
    // changes the outputNeuronActivations
    public int getWinningNeuronId(final double inputPattern[],
                                  double normalizationFactor[],
                                  double syntheticLastInput[])
    {
        int i;
        int winningNeuronId;
        double maxOutputNeuronActivation;
        double neuronConnectionsStrengths[];
        double bias;
        //

        // 1.- Normalize the input
        // and obtain normalization factor and synthetic last input
        normalizationFactor = getNormalizationFactor(inputPattern,
                                                     normalizationFactor);
        syntheticLastInput[0] = 0.0;

        // 2.- For Each output neuron
        maxOutputNeuronActivation = -1.E30;
        winningNeuronId = 0;
        i = 0;
        while(i < numberOfNeurons)
        {
            neuronConnectionsStrengths = connectionsStrengths[i];
            // 3.- Calculate the “dot product” of the input neurons
            // and their connection weights
            // and Normalizing the output neuron
            bias = neuronConnectionsStrengths[numberOfInputUnits];
                    neuronActivationValues[i] = FunctionLibrary.dotProduct(inputPattern,neuronConnectionsStrengths) *
                                        normalizationFactor[0] +
                                        bias * syntheticLastInput[0];
            // 4.- Bipolar mapping
            neuronActivationValues[i] = 0.5 * (neuronActivationValues[i] + 1.0) ;
            // 5.- Determine the winning neuron
            if(neuronActivationValues[i] > maxOutputNeuronActivation)
            {
                maxOutputNeuronActivation = neuronActivationValues[i] ;
                winningNeuronId = i;
            }//end if

            // 6.- Account for rounding
            if ( neuronActivationValues[i] > 1.0 )
                neuronActivationValues[i] = 1.0 ;
            //end if
            if ( neuronActivationValues[i] < 0.0 )
                neuronActivationValues[i] = 0.0 ;
            //end if
            i = i + 1;
        }//end while

        return winningNeuronId;
    }//end getWinningNeuron





    /**
     * This method is called at the end of a training iteration.
     * This method adjusts the weights based on the previous trial.
     *
     * @param winnerCount
     * @param maxError
     * @param correction
     */
    // Obtains the maxError[0]
    public double getMaxError(final int winnerCount[],
                              final double correction[][],
                              double maxError[])
    {
        double correctionFactor;
        double correctionVector[];
        double connectionsVector[];
        double length;
        double factor;
        int i;
        int j;
        //
        maxError[0] = 0.0 ;

        i = 0;
        while(i < numberOfNeurons )
        {
            if (winnerCount[i] == 0)
                continue;
            //end if
            connectionsVector = connectionsStrengths[i];
            correctionVector = correction[i];
            factor = 1.0 / (double) winnerCount[i];
            if ( learningMethod != 0 )
                factor = factor * learningRate;
            //end if
            length = 0.0;

            j = 0;
            while( j <= numberOfInputUnits )
            {
                correctionFactor = factor * correctionVector[j];
                connectionsVector[j] = connectionsVector[j] + correctionFactor;
                length = length + correctionFactor * correctionFactor;
                j = j + 1;
            }//end while

            if ( length > maxError[0] )
                maxError[0] = length;
            //end if

            i = i + 1;
        }//end while

        // scale the correction
        maxError[0] = Math.sqrt ( maxError[0] ) / learningRate ;
        return maxError[0];
    }//end getMaxError






    /**
     * Normalize weights
     *
     * @param weights  weights
     */
    void normalizeWeights(double weights[])
    {
        int i;
        double length;
        //

        length = FunctionLibrary.vectorLength(weights) ;
        // just in case it gets too small
        if (length < 1.E-30 )
            length = 1.E-30 ;
        //end if

        length = 1.0 / Math.sqrt (length) ;

        i = 0;
        while(i < numberOfInputUnits)
        {
            weights[i] = weights[i] * length;
            i = i + 1;
        }//end while

        weights[numberOfInputUnits] = 0;

    }//end normalizeWeights









    /**
     * This method does much of the work of the learning process.
     * This method evaluates the weights against the training
     * set.
     *
     * @param winnerCount
     * @param error
     * @param correction
     * @param workArea
     * @exception java.lang.RuntimeException
     */
    void evaluateError(int winnerCount[], double error[],
                       double correction[][],double workArea[])
            throws RuntimeException
    {
        int best;
        int trainingSetNumber;
        double theTrainingSet[];
        double normalizationFactor[];
        double syntheticLastInput[];
        double winnerCorrection[];
        double winnerConnection[];
        double length;
        double difference;
        int y;
        int x;
        int i;
        //
        normalizationFactor = new double[1];
        syntheticLastInput = new double[1];

        // reset correction and winner counts
        y = 0;
        while(y < correction.length)
        {
            x = 0;
            while(x < correction[0].length)
            {
                correction[y][x] = 0;
                x = x + 1;
            }//end while
            y = y + 1;
        }//end while

        i = 0;
        while(i < winnerCount.length)
        {
            winnerCount[i] = 0;
            i = i + 1;
        }//end while

        error[0] = 0.0 ;

        // loop through all training sets to determine correction
        trainingSetNumber = 0;
        while(trainingSetNumber < trainingSet.getTrainingSetSize())
        {
            theTrainingSet = trainingSet.getInputSet(trainingSetNumber);

            System.out.println("### OUTPUT NEURON ACTIVATIONS before get winning neuron ###");
            FunctionLibrary.showVector(neuronActivationValues);

            best = getWinningNeuronId(theTrainingSet,
                    normalizationFactor,syntheticLastInput);

            System.out.println("### OUTPUT NEURON ACTIVATIONS after got winning neuron ###");
            FunctionLibrary.showVector(neuronActivationValues);

            winnerCount[best] = winnerCount[best] + 1;
            winnerConnection = connectionsStrengths[best];
            winnerCorrection = correction[best];
            length = 0.0;

            i = 0;
            while(i < numberOfInputUnits)
            {
                difference = theTrainingSet[i] * normalizationFactor[0] -
                        winnerConnection[i] ;
                length = length + difference * difference ;
                if (learningMethod != 0)
                    winnerCorrection[i] = winnerCorrection[i] + difference ;
                    //end if
                else
                {
                    workArea[i] = learningRate * theTrainingSet[i] * normalizationFactor[0] +
                            winnerConnection[i];
                }//end else
                i = i + 1;
            }//end while

            difference = syntheticLastInput[0] - winnerConnection[numberOfInputUnits];
            length = length + difference * difference ;

            if (learningMethod != 0)
                winnerCorrection[numberOfInputUnits] = winnerCorrection[numberOfInputUnits] +
                        difference ;
                //end if
            else
            {
                workArea[numberOfInputUnits] = learningRate * syntheticLastInput[0] +
                        winnerConnection[numberOfInputUnits];
            }//end else

            if ( length > error[0] )
                error[0] = length ;
            //end if

            if (learningMethod == 0)
            {
                normalizeWeights(workArea);
                i = 0;
                while(i <= numberOfInputUnits)
                {
                    winnerCorrection[i] = winnerCorrection[i] + workArea[i] - winnerConnection[i];
                    i = i + 1;
                }//end while
            }//end if

            trainingSetNumber = trainingSetNumber + 1;
        }//end while

        error[0] = Math.sqrt (error[0]) ;

    }//end evaluateErrors



    /**
     * If no neuron wins, then force a winner.
     *
     * @param winnerCount
     * @exception java.lang.RuntimeException
     */
    void forceAWinner(final int winnerCount[]) throws RuntimeException
    {
        int i;
        int traningSetIndex;
        int best;
        int size;
        int which;
        double inputSet[];
        double normalizationFactor[];
        double distance;
        double connectionsVector[];
        double syntheticLastInput[];
        //
        which = 0;
        syntheticLastInput = new double[1];
        normalizationFactor = new double[1];
        size = numberOfInputUnits + 1 ;
        distance = 1.E30 ;

        traningSetIndex = 0;
        while(traningSetIndex < trainingSet.getTrainingSetSize())
        {
            inputSet = trainingSet.getInputSet(traningSetIndex);
            best = getWinningNeuronId(inputSet,
                    normalizationFactor,syntheticLastInput) ;
            if ( neuronActivationValues[best] < distance )
            {
                distance = neuronActivationValues[best];
                which = traningSetIndex;
            }//end if
            traningSetIndex = traningSetIndex + 1;
        }//end while

        inputSet = trainingSet.getInputSet(which);

        best = getWinningNeuronId(inputSet,
                normalizationFactor,syntheticLastInput);

        distance = -1.e30 ;
        i = numberOfNeurons;
        while ( i > 0 )
        {
            i = i - 1;
            if (winnerCount[i] != 0)
                continue ;
            //end if
            if ( neuronActivationValues[i] > distance )
            {
                distance = neuronActivationValues[i];
                which = i ;
            }//end if
        }//end while

        connectionsVector = connectionsStrengths[which];
        System.arraycopy(inputSet,0,connectionsVector,0,
                inputSet.length);

        connectionsVector[numberOfInputUnits] = syntheticLastInput[0] / normalizationFactor[0];
        normalizeWeights(connectionsVector);
    }//end forceAWinner





    /**
     * This method is called to train the network. It can run
     * for a very long time and will report progress back to the
     * owner object.
     *
     * @exception java.lang.RuntimeException
     */
    public void learn() throws RuntimeException
    {
        int i;
        int trainingSetIndex;
        int trialCounter;
        int winnerCount[];
        int winners ;
        double workArea[];
        double correctionMatrix[][];
        double theLearningRate;
        double maxMeanSquareError;
        double inputTrainingSet[];
        double error[];
        double maxError[];
        KohonenNetwork bestNetwork;
        //
        // 1.- creates and initializes error and mean square error
        error = new double[1] ;
        maxError = new double[1];
        meanSquareError = 1.0;
        // 2.- Confirms that normalization will be possible
        trainingSetIndex = 0;
        while(trainingSetIndex < trainingSet.getTrainingSetSize())
        {
            inputTrainingSet = trainingSet.getInputSet(trainingSetIndex);
            if (FunctionLibrary.vectorLength(inputTrainingSet) < 1.E-30 )
            {
                throw(new RuntimeException("Multiplicative normalization has null training case"));
            }//end if
            trainingSetIndex = trainingSetIndex + 1;
        }//end while
        // 3.- Creates a Kohonen neural network
        bestNetwork = new KohonenNetwork(numberOfInputUnits,numberOfNeurons,
                controller);
        System.out.println("%%% NEURAL NETWORK Neurons %%%");
        System.out.println(" Number of INPUT neurons: " + numberOfInputUnits);
        System.out.println(" Number of OUTPUT neurons: " + numberOfNeurons);
        // 4.- creates the winner count array and the correction matrix
        winnerCount = new int[numberOfNeurons];
        correctionMatrix = new double[numberOfNeurons][numberOfInputUnits + 1];
        // 5.- If the learning method is additive, creates a work area vector
        if (learningMethod == 0)
            workArea = new double[numberOfInputUnits + 1];
            //end if
        else
            workArea = null;
        //end else
        // 6.- clears, randomize and normalize the outputWeights vector
        initialize();
        System.out.println("%%% CONNECTIONS STRENGTHS %%%");
        FunctionLibrary.showMatrix(connectionsStrengths);
        // iterative process
        theLearningRate = learningRate;
        maxMeanSquareError = 1.e30;
        trialCounter = 0;
        while(true)
        {
            // 7.- Obtains the error vector and the work area
            evaluateError(winnerCount,error,correctionMatrix,workArea);
            System.out.println("");
            System.out.println("%%% ERROR %%%");
            System.out.println(error[0]);
            System.out.println("");
            meanSquareError = error[0];
            // 8.- if the meanSquareError is smaller then the error quit threshold
            // the iteration stops.
            if (error[0] < errorQuitThreshold)
                break;
            //end if
            // 9.- updates the max square error
            // and copies the weights of the best network into this network
            if (meanSquareError < maxMeanSquareError)
            {
                maxMeanSquareError = meanSquareError;
                copyConnectionsStrengths(bestNetwork ,this);
            }//end if
            // 10.- determines the number of winners
            winners = 0;
            i = 0;
            while(i < winnerCount.length)
            {
                if (winnerCount[i] != 0)
                    winners = winners + 1;
                //end if
                i = i + 1;
            }//end while
            // 11.- if the number of winners is smaller than the number of output neurons
            // and smaller then the number of training sets, no neuron wins
            // then we force a winner
            // and returns to the initial part of the iteration (evaluate errors)
            if (    (winners < numberOfNeurons) &&
                    (winners < trainingSet.getTrainingSetSize()))
            {
                forceAWinner(winnerCount);
                continue ;
            }//end if
            // 12.- adjust weights
            maxError[0] = getMaxError(winnerCount,correctionMatrix,
                    maxError);
            // 13.- update the statistics to show at the view
            controller.updateStatistics(trialCounter,meanSquareError,
                    maxMeanSquareError);
            // 14.- If has to stop update statistics
            if(hasToAbortLearning)
            {
                controller.updateStatistics(trialCounter,meanSquareError,
                        maxMeanSquareError);
                break;
            }//end if
            // 15.- Yields the CPU to other thread
            Thread.yield();
            // 16.- if the max erros is smaller than 0.00001
            if (maxError[0] < 1E-5)
            {
                trialCounter = trialCounter + 1;
                if (trialCounter > retriesBeforeQuit)
                    break ;
                //end if
                initialize();
                theLearningRate = learningRate;
                continue;
            }//end if
            if ( theLearningRate > 0.01 )
                theLearningRate = theLearningRate * reductionFactor;
            //end if
        }//end while
        copyConnectionsStrengths(this,bestNetwork);
        i = 0;
        while(i < numberOfNeurons)
        {
            normalizeWeights(connectionsStrengths[i]);
            i = i + 1;
        }//end while
        hasToAbortLearning = true;
        trialCounter = trialCounter + 1;
        controller.updateStatistics(trialCounter,meanSquareError,maxMeanSquareError);
        FunctionLibrary.showMatrix(connectionsStrengths);
    }//end learn

}//end class KohonenNetwork