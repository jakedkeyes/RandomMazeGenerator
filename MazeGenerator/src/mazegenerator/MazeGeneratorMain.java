/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mazegenerator;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

/**
 *
 * @author 1019011
 */
public class MazeGeneratorMain {
    public static int width, height, heightCount, widthCount, RandomWallLocalHeightCount = 0, RandomWallLocalWidthCount = 0;
    public static MazeRoom[][] room;
    public static int localHeightCount = 0, localWidthCount = 0;
    public static ArrayList<MazeRoom> inSet = new ArrayList();
    public static void main(String args[]){
        Scanner input = new Scanner(System.in);
        System.out.print("Welcome to the random maze generator!\n\nEnter the width (between 2 and 50)");

        width = input.nextInt();
        while (width > 50 || width < 2){
        System.out.print("\nPlease enter a valid integer for the width.\n");
        width = input.nextInt();
        }
        System.out.print("Please enter the height(between 2 and 50)");
        height = input.nextInt();
        while (height < 2 || height > 50){
            System.out.print("\nPlease enter a valid integer for the height.\n");
            height = input.nextInt();
        }
        room = new MazeRoom[height][width];
        while(heightCount < height){
            while(widthCount < width){
                room[heightCount][widthCount] = new MazeRoom();
                widthCount++;
            }
            heightCount++;
            widthCount = 0;
        }
        heightCount = 0;
        widthCount = 0;
        room[0][0].isConnected = true;

         while(!room[0][0].allConnected()){
            room[localHeightCount][localWidthCount].destroyRandomWall(localHeightCount, localWidthCount);
            localHeightCount = RandomWallLocalHeightCount;
            localWidthCount = RandomWallLocalWidthCount;
        }
        room[0][0].wall[0] = ' ';
        room[height-1][width-1].wall[2] = ' ';
        printMaze();
    }
    public static void printMaze(){

        localHeightCount = 0;
        localWidthCount = 0;

        for(localHeightCount = 0; localHeightCount < height; localHeightCount++){
            if(localHeightCount == 0){
                for(localWidthCount = 0; localWidthCount < width; localWidthCount++){
                    System.out.print(" " + room[localHeightCount][localWidthCount].wall[0]);
                }
                    System.out.println();
            }
            for(localWidthCount = 0; localWidthCount < width; localWidthCount++){
                System.out.print(room[localHeightCount][localWidthCount].wall[1]);
                System.out.print(room[localHeightCount][localWidthCount].wall[3]);

                if(localWidthCount == width - 1)
                {
                    System.out.print(room[localHeightCount][localWidthCount].wall[2]);
                }
            }
            System.out.println();
        }
    }

    public static class MazeRoom {
        char[] wall = new char[4];
        public MazeRoom(){
            wall[0] = '_';
            wall[1] = '|';
            wall[2] = '|';
            wall[3] = '_';
        }
        public int getTopRoom(int localHeightCount){
            return localHeightCount - 1;
        }
        public int getLeftRoom(int localWidthCount){
            return localWidthCount - 1;
        }
        public int getRightRoom(int localWidthCount){
            return localWidthCount + 1;
        }
        public int getBottomRoom(int localHeightCount){
            return localHeightCount + 1;
        }
        public boolean allConnected(){
            int localHeightCount = 0, localWidthCount = 0;
            while(localHeightCount < height){
                while(localWidthCount < width){
                    if(!room[localHeightCount][localWidthCount].isConnected){
                        return false;
                    }
                    localWidthCount++;
                }
                localHeightCount++;
                localWidthCount = 0;
            }
            return true;
        }

        public boolean isTopRoom(int localHeightCount){
            if(localHeightCount == 0){
                return false;
            }
            else{
                return true;
            }
        }
        public boolean isLeftRoom(int localWidthCount){
            if(localWidthCount == 0){
                return false;
            }
            else{
                return true;
            }
        }
        public boolean isRightRoom(int localWidthCount){
            if(localWidthCount < width - 1){
                return true;
            }
            else{
                return false;
            }
        }
        public boolean isBottomRoom(int localHeightCount){
            if(localHeightCount < height - 1){
                return true;
            }
            else{
                return false;
            }
        }

        public void destroyRandomWall(int localHeightCount, int localWidthCount){
            Random rnd = new Random();
            int randomWall = rnd.nextInt(4);
            boolean done = false;
            RandomWallLocalHeightCount = localHeightCount;
            RandomWallLocalWidthCount = localWidthCount;
            if((room[localHeightCount][localWidthCount].isTopRoom(localHeightCount) && !room[localHeightCount-1][localWidthCount].isConnected && room[localHeightCount][localWidthCount].isConnected) || (room[localHeightCount][localWidthCount].isBottomRoom(localHeightCount) && !room[localHeightCount+1][localWidthCount].isConnected && room[localHeightCount][localWidthCount].isConnected) || (room[localHeightCount][localWidthCount].isLeftRoom(localWidthCount) && !room[localHeightCount][localWidthCount-1].isConnected && room[localHeightCount][localWidthCount].isConnected) || (room[localHeightCount][localWidthCount].isRightRoom(localWidthCount) && !room[localHeightCount][localWidthCount+1].isConnected && room[localHeightCount][localWidthCount].isConnected)){
                while(!done){
                    if(randomWall == 0){
                        if(room[localHeightCount][localWidthCount].isTopRoom(localHeightCount)){
                            if(!room[localHeightCount - 1][localWidthCount].isConnected){
                                printMaze();
                                room[localHeightCount][localWidthCount].wall[0] = ' ';
                                room[localHeightCount - 1][localWidthCount].wall[3] = ' ';
                                room[localHeightCount - 1][localWidthCount].isConnected = true;
                                done = true;
                                RandomWallLocalHeightCount--;
                                inSet.add(room[localHeightCount - 1][localWidthCount]);
                            }
                            else{
                                randomWall = rnd.nextInt(4);
                            }
                        }
                        else{
                            randomWall = rnd.nextInt(4);
                        }
                    }
                    else if(randomWall == 1){
                        if(room[localHeightCount][localWidthCount].isLeftRoom(localWidthCount)){
                            if(!room[localHeightCount][localWidthCount - 1].isConnected){
                                printMaze();
                                room[localHeightCount][localWidthCount].wall[1] = ' ';
                                room[localHeightCount][localWidthCount - 1].wall[2] = ' ';
                                room[localHeightCount][localWidthCount - 1].isConnected = true;
                                done = true;
                                RandomWallLocalWidthCount--;
                                inSet.add(room[localHeightCount][localWidthCount - 1]);
                            }
                            else{
                                randomWall = rnd.nextInt(4);
                            }
                        }
                        else{
                            randomWall = rnd.nextInt(4);
                        }
                    }
                    else if(randomWall == 2){
                        if(room[localHeightCount][localWidthCount].isRightRoom(localWidthCount)){
                            if(!room[localHeightCount][localWidthCount + 1].isConnected){
                                printMaze();
                                room[localHeightCount][localWidthCount].wall[2] = ' ';
                                room[localHeightCount][localWidthCount + 1].wall[1] = ' ';
                                room[localHeightCount][localWidthCount + 1].isConnected = true;
                                done = true;
                                RandomWallLocalWidthCount++;
                                inSet.add(room[localHeightCount][localWidthCount + 1]);
                            }
                            else{
                                randomWall = rnd.nextInt(4);
                            }
                        }
                        else{
                            randomWall = rnd.nextInt(4);
                        }
                    }
                    else if(randomWall == 3){
                        if(room[localHeightCount][localWidthCount].isBottomRoom(localHeightCount)){
                            if(!room[localHeightCount + 1][localWidthCount].isConnected){
                                printMaze();
                                room[localHeightCount][localWidthCount].wall[3] = ' ';
                                room[localHeightCount + 1][localWidthCount].wall[0] = ' ';
                                room[localHeightCount + 1][localWidthCount].isConnected = true;
                                done = true;
                                RandomWallLocalHeightCount++;
                                inSet.add(room[localHeightCount + 1][localWidthCount]);
                            }
                            else{
                                randomWall = rnd.nextInt(4);
                            }
                        }
                        else{
                            randomWall = rnd.nextInt(4);
                        }
                    }
                }
            }
            else{
                RandomWallLocalHeightCount = rnd.nextInt(height);
                RandomWallLocalWidthCount = rnd.nextInt(width);
            }
        }
        public boolean isConnected;
    }
}


