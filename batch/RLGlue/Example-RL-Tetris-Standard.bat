cd ..
cd ..
java -jar dist/MM-NEATv2.jar runNumber:%1 randomSeed:%1 base:tetris trials:1 maxGens:500 mu:50 io:true netio:true mating:true task:edu.utexas.cs.nn.tasks.rlglue.RLGlueTask cleanOldNetworks:true fs:false noisyTaskStat:edu.utexas.cs.nn.util.stats.Average log:RL-Standard saveTo:Standard rlGlueEnvironment:org.rlcommunity.environments.tetris.Tetris rlGlueExtractor:edu.utexas.cs.nn.tasks.rlglue.featureextractors.tetris.ModelFreeTetrisExtractor moTetris:true