cd ..
cd ..
java -jar dist/MM-NEATv2.jar runNumber:%1 randomSeed:%1 maxGens:200 mu:25 io:true netio:true mating:true base:HNMsPacMan task:edu.utexas.cs.nn.tasks.mspacman.MsPacManTask cleanOldNetworks:true pacManLevelTimeLimit:8000 pacmanInputOutputMediator:edu.utexas.cs.nn.tasks.mspacman.sensors.MsPacManHyperNEATMediator trials:3 log:HyperNEAT-testFullEverything saveTo:testFullEverything hyperNEAT:true genotype:edu.utexas.cs.nn.evolution.genotypes.HyperNEATCPPNGenotype allowMultipleFunctions:true fs:false ftype:1 netChangeActivationRate:0.3 pacManFullScreenOutput:true pacmanFullScreenPowerInput:true pacmanBothThreatAndEdibleSubstrate:true pacmanFullScreenProcess:true