cd ..
cd ..
java -jar target/MM-NEAT-0.0.1-SNAPSHOT.jar runNumber:%1 randomSeed:%1 base:tetris trials:3 maxGens:500 mu:50 io:true netio:true mating:true task:edu.southwestern.tasks.rlglue.tetris.HyperNEATTetrisTask  rlGlueEnvironment:org.rlcommunity.environments.tetris.Tetris rlGlueExtractor:edu.southwestern.tasks.rlglue.featureextractors.tetris.RawTetrisStateExtractor tetrisTimeSteps:true tetrisBlocksOnScreen:false  rlGlueAgent:edu.southwestern.tasks.rlglue.tetris.TetrisAfterStateAgent splitRawTetrisInputs:true senseHolesDifferently:true hyperNEAT:true genotype:edu.southwestern.evolution.genotypes.HyperNEATCPPNGenotype allowMultipleFunctions:true ftype:1 netChangeActivationRate:0.3 substrateMapping:edu.southwestern.networks.hyperneat.BottomSubstrateMapping steps:500000 perLinkMutateRate:0.05 netLinkRate:0.4 netSpliceRate:0.2 crossoverRate:0.5 log:Tetris-CNNMSSHeterogeneousFieldsD1W3 saveTo:CNNMSSHeterogeneousFieldsD1W3 convolution:true useHyperNEATCustomArchitecture:true hyperNEATCustomArchitecture:edu.southwestern.networks.hyperneat.architecture.HeterogeneousFieldsD1W3 tetrisAllowLine:false tetrisAllowSquare:false tetrisAllowTri:false tetrisAllowLShape:false tetrisAllowJShape:false