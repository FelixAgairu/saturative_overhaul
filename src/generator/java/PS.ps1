$Root = ".\src\generator\java\generator"
$GSON = ".\src\generator\java\gson-2.13.2.jar"

# Compile
javac -cp ".;$GSON" "$Root\Generator.java"

# Run
java -cp ".;$GSON;$Root" "Generator"