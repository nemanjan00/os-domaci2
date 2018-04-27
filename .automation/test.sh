echo "[=] Compiling FAT16Test"

javac -cp ./src:./assets/junit.jar -d ./bin ./src/rs/raf/os/test/FAT16Test.java

if [ $? -eq 0 ]
then
	echo "[+] Successfully compiled FAT16Test"
else
	echo "[-] Could not compile FAT16Test" >&2
	exit 1
fi

echo "[=] Testing FAT16Test"

java -cp ./bin:./assets/junit.jar:./assets/hamcrest-core.jar org.junit.runner.JUnitCore rs.raf.os.test.FAT16Test

if [ $? -eq 0 ]
then
	echo "[+] Successfully tested FAT16"
else
	echo "[-] Some tests in FAT16Test failed" >&2
	exit 1
fi

echo "Compiling DirectoryTest"

javac -cp ./src:./assets/junit.jar -d ./bin ./src/rs/raf/os/test/DirectoryTest.java

if [ $? -eq 0 ]
then
	echo "[+] Successfully compiled DirectoryTest"
else
	echo "[-] Could not compile DirectoryTest" >&2
	exit 1
fi

echo "[=] Testing DirectoryTest"

java -cp ./bin:./assets/junit.jar:./assets/hamcrest-core.jar org.junit.runner.JUnitCore rs.raf.os.test.DirectoryTest

if [ $? -eq 0 ]
then
	echo "[+] Successfully tested Directory"
else
	echo "[-] Some tests in DirectoryTest failed" >&2
	exit 1
fi

