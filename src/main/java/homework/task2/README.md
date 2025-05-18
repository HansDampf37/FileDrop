# FunctionClass
This repo contains a java File `FunctionClass` that does three things:
1. unzip an archive containing a list of text files
2. merge the extracted text files into a new file
3. zip the three original files together with the merged file into a new archive

An archive for testing the script is given with `archive.zip` containing the files `file[1-3].zip`
To run the script run:
```bash
cd ..
javac main.java.homework.task2/FunctionClass.java
java main.java.homework.task2/FunctionClass main.java.homework.task2/archive.zip main.java.homework.task2/merged_archive.zip
rm main.java.homework.task2/FunctionClass.class
```
# SplitFile
It also contains SplitFile, a script to split a file into a set of files of fixed size.
To run the script you can for example run:
```bash
cd ..
javac main.java.homework.task2/SplitFile.java
java main.java.homework.task2/SplitFile main.java.homework.task2/README.md 100
rm main.java.homework.task2/SplitFile.class
```

# FolderTraversal 
Finally, there is also a FolderTraversal.java file that prints the contents of a folder + its checksums.
To run it:
```bash
cd ..
javac main.java.homework.task2/FolderTraversal.java
java main.java.homework.task2/FolderTraversal "$(pwd)"
rm main.java.homework.task2/FolderTraversal.class 
```