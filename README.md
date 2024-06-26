# Tugas Kecil 3 Strategi Algoritma

## Table of Contents
* [General Information](#general-information)
* [Technologies Used](#technologies-used)
* [Requirements](#requirements)
* [How to Run and Compile](#howtorunandcompile)
* [Screenshots](#screenshots)
* [Program Structure](programstructure)
* [Authors](#authors)


## General Information
This project is a made with Java program designed to find the shortest transformation sequence from one word to another by changing a single letter at a time, with each transformed word needing to be a valid word within a given dictionary. This project implements three distinct algorithms to tackle the Word Ladder problem: Uniform Cost Search (UCS), Greedy Best First Search, and A* Search, allowing for a comparison of their effectiveness and efficiency in various scenarios.

## Technologies Used
- openjdk 17.0.8.1
- OpenJDK Runtime Environment Temurin-17.0.8.1+1
- OpenJDK 64-Bit Server VM Temurin-17.0.8.1+1


## Requirements
To run this program, you need to install Java 17 on the device you are using 
(https://www.oracle.com/id/java/technologies/downloads/)

## **How to Run and Compile (Windows)**
### **Setup**
1. Clone this repository <br>
```sh 
$ git clone git@github.com:JonathanSaragih/Tucil3_13522121.git
```
2. Open this repository in terminal

### **Run**
1. Compile program <br>
```sh 
$ javac src/Main.java src/WordLadderSolver.java src/DictionaryLoader.java
```

2. Run program <br>
```sh 
$ java src.Main
```

## Screenshots
<img src="test/image.png">

## Program Structure
```
.
 ┣ doc
 ┃ ┗ Tucil3_13522121.pdf
 ┣ src
 ┃ ┣ DictionaryLoader.java
 ┃ ┣ Main.java
 ┃ ┣ WordLadderSolver.java
 ┃ ┣ words.txt
 ┃ 
 ┣ test
 ┃ ┣ Screenshoot
 ┃ 
 ┗ README.md
```

## Contact
* Created by [@JonathanSaragih](https://www.linkedin.com/in/jonathan-emmanuel-saragih-273596286/) - feel free to contact me!
* Class : K03
* NIM : 13522121
