cd ..
cd ..
java -jar dist/MM-NEATv2.jar runNumber:%1 randomSeed:%1 base:vizdoomdefendline trials:5 maxGens:100 mu:10 io:true netio:true mating:true task:edu.utexas.cs.nn.tasks.vizdoom.VizDoomDefendLineTask cleanOldNetworks:true fs:false noisyTaskStat:edu.utexas.cs.nn.util.stats.Average log:DefendLine-Control saveTo:Control gameWad:freedoom2.wad watch:false stepByStep:false doomEpisodeLength:2100 printFitness:true