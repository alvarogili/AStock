
java -cp build/classes;lib/jpox-enhancer-1.2.2.jar;lib/jdo2-api-2.0.jar;lib/jpox-java5-1.2.3.jar;lib/jpox-core-1.2.2.jar;lib/asm-all-3.2.jar;lib/log4j-1.2.16.jar;lib/bcel-5.2.jar -Dlog4j.configuration=file:log4j.properties org.jpox.enhancer.JPOXEnhancer src/BD/*.jdo
java -cp build/classes;lib/jpox-enhancer-1.2.2.jar;lib/jdo2-api-2.0.jar;lib/jpox-java5-1.2.3.jar;lib/jpox-core-1.2.2.jar;lib/asm-all-3.2.jar;lib/log4j-1.2.16.jar;lib/bcel-5.2.jar -Dlog4j.configuration=file:log4j.properties org.jpox.enhancer.JPOXEnhancer src/BD/Agenda/*.jdo
java -cp build/classes;lib/jpox-enhancer-1.2.2.jar;lib/jdo2-api-2.0.jar;lib/jpox-java5-1.2.3.jar;lib/jpox-core-1.2.2.jar;lib/asm-all-3.2.jar;lib/log4j-1.2.16.jar;lib/bcel-5.2.jar -Dlog4j.configuration=file:log4j.properties org.jpox.enhancer.JPOXEnhancer src/BD/Utilidades/*.jdo
cp src/BD/*.jdo build
cp src/BD/Agenda/*.jdo build
cp src/BD/Utilidades/*.jdo build
