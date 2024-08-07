import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        byte[] chars,charsbkp =new byte[263]; //This is the amount of the whole array in the ROM. This is for all eight characters.
        byte[] rom=readRom("/media/work/devver/disas/HOTL/anotherexam/editor/Lance.sms");
        //byte[] rom=readRom(//media//levi//Work//devver//disas//HOTL//anotherexam//editor//);
        String[] charnames={"Goldmoon","Sturm","Caramon","Raistlin","Tanis","Tasslehoff","Riverwind","Flint"};
        String[] itemNames={    //The glitched names and other stuff is not included, just what's okay and won't glitch the game out.
                "No Ranged Weapon\\Empty",
                "Blue Crystal Staff",
                "Staff of Magius",
                "Bow",
                "Longsword",
                "Dagger",
                "Hoopak",
                "Jo Stick",
                "Hunting Knife",
                "Spear",
                "Two handed Sword",
                "Hand Axe",
                "Sword",
                "Sword",
                "Green Quiver",
                "Red Quiver",
                "Pouch",
                "Bracelet",
                "Shield",
                "Shield",
                "Shield",
                "Shield",
                "Shield",
                "Gem",
                "Gem",
                "Gem",
                "Gem",
                "Gem",
                "Gold Bar",
                "Silver Bar",
                "Silver Chalice",
                "Coins",
                "TBD",
                "TBD",
                "TBD",
                "TBD",
                "TBD",
                "TBD",
                "TBD",
                "Bow",
                "Longsword",
                "Sword",
                "Dagger",
                "Hunting Knife",
                "Scroll",
                "Scroll",
                "Green Potion",
                "Orange Potion",
                "Red Potion",
                "Blue Potion",
                "Yellow Potion",
                "Ring",
                "Gem Ring",
                "Wand",
                "Disks of Mishakal",
                "Brown Potion",
                "Game glitch",  //Probably not an item.
                "No Name",
                "Glitch name",
                "Glitch name",
                "Glitch name",
                "Glitch name",
                "Glitch name",
                "Sturm\\Falling Stone",
                "Caramon\\Falling Stone",
                "Raistlin\\Falling Stone",
                "Tanis\\Falling Stone",
                "Tasslehoff\\Small Falling Stones",
                "Riverwind\\Falling Stone",
                "Flint\\Blue rising Proj",
                "Dead Character\\Fire on ground",
                "Glitch\\Arrow Trap",
                "Glitch\\Falling Stone",
                "Glitch\\Falling Stone" //I guess the item\trap names are just falling stones from now.

        };
 /*       for (int i = 0; i <= 16; i++) {
            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
            //System.out.println("i = " + i);
            System.out.print(String.format("0x%02X",rom[i])+"    \n");

        } */
        chars=getcharstat(rom);
        charsbkp=getcharstat(rom);
        //printchrinv(6,chars,itemNames);
        //printchrstats(1,chars,itemNames);
        byte[] items=extractItems(rom);
        //printItems(items,itemNames);
        //saverom2(rom);
        //printNme(rom);
        //printlvlPointerDetails(1,rom);
        printDoorDetails(rom);
        
        //There are 87 entries as rooms.
        //prnLvlptrDet(1,rom);  //This is not working correctly sadly.
    }
    public static List retRoomdtls(byte[] rom){
        List<String> result=new ArrayList<>();
            
        

        return result;

    }
    public static void writeCSV(List input){
try {
    // create a list of objects
    List<List<String>> records = Arrays.asList(
            Arrays.asList("1", "John Lee", "US"),
            Arrays.asList("2", "Jovan Roover", "DE"),
            Arrays.asList("3", "Emma Watson", "UK")
    );

    // create a writer
    BufferedWriter writer = Files.newBufferedWriter(Paths.get("users-with-header.csv"));

    // write header record
    writer.write("ID,Name,Country");
    writer.newLine();

    // write all records
    for (List<String> record : records) {
        writer.write(String.join(",", record));
        writer.newLine();
    }

    //close the writer
    writer.close();

} catch (IOException ex) {
    ex.printStackTrace();
}

    }
    public static void CheckCSV(String filepaString){
        File csvFile = new File(filepaString);
            if (csvFile.isFile()) {
                // create BufferedReader and read data from csv
                System.out.println("The input csv file is there. Cool.");
            }
                else{
                        System.out.println("Ayy, the csv is not present or inaccessible. Exiting.");
                        System.exit(-12);

                    }
    }
    public static String[] OpenCSV(String filepathString){  //Does as it says on the tin. You get a string array back, and have to process it further.
        String[]result={};
	try {
    // CSV file delimiter
    String DELIMITER = ",";

    // create a reader
    BufferedReader br = Files.newBufferedReader(Paths.get("users.csv"));

    // read the file line by line
    String line;
    while ((line = br.readLine()) != null) {
        // convert line into tokens
        String[] tokens = line.split(DELIMITER);

        // TODO: do something here with the data

        // print all tokens
        for (String token : tokens) {
            System.out.println(token);
        }
    }

    // close the reader
    br.close();

} catch (IOException ex) {
    ex.printStackTrace();
}

    
    
    
    
    
    
    

        return result;

    }
    public static byte[] getcharstat (byte[] rom){  //Extracts character stats from the ROM.
    byte[] charstat=new byte[263];
    int romoffset1=0x7D6C;
        for (int i = 0; i < charstat.length; i++) {
        charstat[i]=rom[i+romoffset1];
        }

    return charstat;
    }   //Get the character stats from the original unaltered ROM.
    public static byte[] bkpstats (byte[] orig){    //Backs up these stats to a backup array, so we will modify this one, instead of the actual one.
    byte[] work=new byte[orig.length];
        for (int i = 0; i < orig.length; i++) {
        work[i]=orig[i];
        }
    return work;
    } //Copies the original character stats into a backup table in RAM.
    public static byte[] readRom (String filename){
        File file =new File(filename);
        int size=(int)file.length();
        byte[] rom=new byte[size];
        try {
            FileInputStream fis = new FileInputStream(new File(filename));
                fis.read(rom,0,rom.length);
                fis.close();
        } catch (IOException ex){

            ex.printStackTrace();
            System.out.println("There was a problem with the file.");
            System.exit(-1);
        }

        return rom;
    }   //Opens the ROM, and copies the contents into an array for further modification.
    public static void printselectchars(){
        System.out.println("Select a character: \n1: Goldmoon\n2: Sturm\n3: Caramon\n4: Raistlin\n5: Tanis\n6: Tasslehoff\n7: Riverwind\n8: Flint");

    }   //Prints the character selection text on the console.
    public static void saverom2(byte[] input){
try {
FileOutputStream fos=new FileOutputStream(new File("//media//work//devver//disas//HOTL//anotherexam//editor//Lance_mod.sms"));
fos.write(input);
fos.close();
}catch (IOException ex){
    ex.printStackTrace();

}

    }   //This works.
    public static void saverom(byte[] input) throws IOException {
        try {
            String name="//media//work//devver//disas//HOTL//anotherexam//editor//Lance_mod.sms";
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(name));
            outputStream.writeObject(input);

        }catch (IOException ex){
            ex.printStackTrace();
            System.out.println("Error while saving file.");
        }


    }   //Saves the result ROM into a file, but it's broken.
    public static void printchrinv(int chr,byte[] itemarray, String[] itemlist){
        String[] charnames={"Goldmoon","Sturm","Caramon","Raistlin","Tanis","Tasslehoff","Riverwind","Flint"};
        System.out.println("Character selected: "+charnames[chr]);
        var offset=chr*33;  //33 bytes per character.
        for (int i = 0; i < 16; i++) {
            System.out.println(i+": "+itemarray[i+offset]+"    "+itemlist[itemarray[i+offset]]);
        }   //Use this offset value, and print the 16 itemslots.
    }  //So far, this prints the inventory of the character, and nothing else.
    public static void printchrstats (int chr, byte[] stats,String[]itemlist){   //Prints the character's known stats on the screen.
        String[] charnames={"Goldmoon","Sturm","Caramon","Raistlin","Tanis","Tasslehoff","Riverwind","Flint"};
        //The character will be the offset for the array as well. One char is still 33 bytes.
        //As for the itemlist, it's the standard one in the game. We still need the name of the stuff.
        String[] choices={"No","Yes"};
        int offset=chr*33+16;
        System.out.println("Selected Character: "+charnames[chr]);
        System.out.println("Starting item: "+itemlist[stats[offset]]+"    -     "+itemlist[stats[offset+1]]); //Print the item name that is coming from the array of the whole shebang.
        //The second one is also this item thing, not sure why the game stores it like that.
        offset+=2;  //Adjust offset for the next value.
        System.out.println("Is Inventory full? "+choices[stats[offset]]+"   "+choices[stats[offset+1]]);
        offset+=2;
        System.out.println("Hitpoints MAX-MIN: "+stats[offset]+"    "+stats[offset+1]);
        offset+=2;
        System.out.println("Damage MIN-MAX "+stats[offset]+"    "+stats[offset+1]);
        offset+=2;
        System.out.println("Damage2 MIN-MAX "+stats[offset]+"    "+stats[offset+1]);      //I don't know these, but definetly some damage values, but not sure what they control. Maybe Physical and Magic Damage.
        offset+=2;
        System.out.println("Strength: "+stats[offset]);
        offset++;
        System.out.println("Intelligence: "+stats[offset]);
        offset++;
        System.out.println("Wisdom: "+stats[offset]);
        offset++;
        System.out.println("Constitution: "+stats[offset]);
        offset++;
        System.out.println("Dexterity: "+stats[offset]);
        offset++;
        System.out.println("Charisma: "+stats[offset]);
        offset++;
        System.out.println("Unknown: "+stats[offset]);  //Same, I have no idea what this is used for.

    }
    public static byte[] extractItems(byte[] rom){
        byte[] items=new byte[500]; //The array in the ROM is this size.
        /* The ROM has fifty items, plus one byte at the end, to mark the end of the item list. The code looks for that one byte, and loads some zeroes as well.
        Every item is five bytes long:
            -First byte is the item type.
            -Second is the chest visibility.
            -Third is the distance in tiles from the beginning of the floor\room.
            -The fourth byte is not yet known, but so far has no effect on the game. Possibly i'm wrong here.
            -Fifth byte marks if the chest can be made visible with the 'Detect Invisible' spell.
        This all is also in the code, but if you're not keen on combing that through, this is a small excerpt.
          */
    var offset=0x7B16; //In the unaltered ROM, this is the address where this array is.
        for (int i = 0; i < items.length; i++) {
            items[i]=rom[i+offset];
        }
        return items;
    }   //The array of items is copied from the ROM to an array, and that's returned. The last zero byte is not included.
    public static void printItems(byte[] items,String[] itemNames) {    //Prints all items in a CSV fashion on the screen.
    String[] visibility={"Visible","Invisible"};
    int roomnr;
    int distance;
    int fourththbyte;
    int chestvisible;
    String chv;

        for (int i = 0; i < items.length/5; i++) {
            roomnr=items[(i*5)+1] & 0xff;
            distance=items[(i*5)+2] & 0xff;
            fourththbyte=items[(i*5)+3] & 0xff;
            chestvisible=items[(i*5)+4] & 0xff;
            if (chestvisible!=0){

            }
            System.out.println(i+": "+itemNames[items[i*5]]+" | Room Nr.: "+roomnr+" \\Hex:"+String.format("$%02X",items[(i*5)+1])+" | Dist from room bgn in tiles: "+distance+"\\Hex:"+String.format("$%02X",distance)+" | 4th byte: "+fourththbyte+" \\ Hex: "+String.format("$%02X",fourththbyte)+" | Chest visible: "+chestvisible+" \\ Hex: "+String.format("$%02X",chestvisible));
        }
    }   //Prints the ROM's items and it seems to be trap content, where they are, and what is in the boxes. Basically we can put traps and items in the same array, and it will get processed. The game's code is pretty flexible this way.
    public static void printNme(byte[] rom){

        String[] nmeTypes={
                "Nothing\\Empty",
                "Goldmoon",
                "Sturm",
                "Caramon",
                "Raistlin",
                "Tanis",
                "Tasslehoff",
                "Riverwind",
                "Flint",
                "Baaz (Grey sword gargoyle)",
                "Blue Gargoyle",
                "Troll",
                "Blue Ghost",
                "Transp. Soldier",
                "Large Dwarf",
                "Dwarf",
                "Soldier",
                "Spider",
                "Small Dragon",
                "Endboss Dragon",
                "Empty\\glitch",
                "Confined Dragon",
                "nothing\\glitch",
                "glitch"
        };
        var arrayoffset=0x06;   //In the code, enemies are stored in the array in six byte groups.
        var offset=0x72d1;      //In the unaltered ROM, this is where the array\\list of the enemies are. Other stuff is also there, but for now, I'm only interested in the room nr, and the monster type, but
        var nmeOffset=offset-1;
        int arraysize=110;
        offset++;           //The room number is the second byte, and every sixth byte after that is the next one.
        var roomnr=0;
        var nmetype=0;
        var nmexcoord=0;
        var screennr=0;
        System.out.println(String.format("0x%02X",rom[offset]));
        for (int i = 0; i < arraysize; i++) {
            roomnr=rom[offset+(i*arrayoffset)]; //Calculate the Room number from the array.

            nmetype=rom[(offset-1)+(i*arrayoffset)];
            nmetype+=8;
            nmexcoord=rom[(offset+1)+(i*arrayoffset)]&0xff;
            screennr=rom[(offset+2)+(i*arrayoffset)]&0xff;
            System.out.println(i+" Room Nr.: "+roomnr+" \\Hex: "+String.format("0x%02X",roomnr)+"   Monster Type: "+nmetype+"   "+nmeTypes[nmetype]+"   "+"Enemy coordinate: "+nmexcoord+"    \\Hex: "+String.format("0x%02X",nmexcoord)
            +"  Screen nr.: "
            +screennr
            );
        }
    }

public static void printDoorDetails(byte[]rom){//In the original ROM, there is an array at 0x190D that holds the door values to other rooms.
    //I don't know how many doors are in the array, so i'll print a few.
    /*
     * 2acc-190d is 11BF which is 4543 bytes. I'm sure this is not all the doors in the game, so there might be some other data in this bunch.
     * There are 88 room entries, so anything over that is invalid probably. The byte definitions are also in the applied hacks disassembly, but i'll
     * put those details here as well:
     * First byte is the compass type. You either can go up or down. $01 is up, $02 is down. There is a compass variable elsewhere that will tell how it should be drawn.
     * Second byte is the room nr where the player will go if (s)he takes the door.
     * Third byte is the column number where the player will end up after taking the door.
     * Fourth byte tells the game which column the door begins.
     * Fifth bye is the end.
     * The door end and beginning is that when the game recognizes some place of the room as a door, it will not draw the door itself.
     * 
     */
var doorOffset=0x190D;
var doorOffsetEnd=0x2acc;
int compassType,roomNr,columnNr,doorStart,doorEnd,doorNr;
String cString="Compass type: ";
String[] cType={"North\\Up","South\\Down"};
String roomString="Room destination: ";
String columnNrString="Destination column in target room: ";
String startColumnString="Door starting column: ";
String endColumnString="Door ending column: ";
//System.out.println(String.format("0x%02X",rom[doorOffset]));


}
public static void printlvlPointerDetails(int roomNr,byte[] rom) {
        List<String> roomDetails=new ArrayList<>(); //What is printed will be returned in a List, so later on, a csv could handle this.
        /*
         * I think for the time, the command line part is also okay, and the data that we will put out would be useable for a GUI stuff.
         */
        
        //This is still work in progress, and now absolute trash.
    	int lvlPointer=0x1343;	//This is the original ROM address for the level pointers.    	//The code will mimic the original written in assembly to extract the data the program will show later.    	//A is coming from the roomNR, which is the input parameter.    	short hl=lvlPointer-2;	//ld hl,_data_1343_lvl_pointers-2    	short de=(short)roomNr+roomNr;	//add a,a    	hl+=de;					//add hl,de    	byte aTemp=rom[hl];	//ld a,(hl)	We make a temp A for the result, but it will be even better later, i'm sure.    	hl++;				//inc hl
    	//System.out.println(String.format("0x%02X", hl));
        //System.out.println(Integer.toBinaryString(hl));
    	int Accumulator; //Z80 has an 8-bit accumulator, what is used for math.
        int H,L,D,E,B,C,I,X,Y =0;   
        int HL,DE,BC,IX,IY=0;
        BC=0;
        int sHL=0,sDE=0,sBC=0,sAccumulator=0;
        int[] stack=new int[255];
        int stackpointer=0;
        int _RAM_DE3E_MAX_LVL_LENGHT; //Level length in the ROM.
        int _DATA_12E1_=0x12E1;
        int _RAM_DE31_METATILE_BANK;
        int _RAM_DE29_METATILE_TILE_LOAD;
        int _RAM_DE32_;
        int _RAM_DE2F_;
        int _RAM_DE2E_BANKSWITCH_LEVEL;
        int _RAM_DE2A_;
        int _RAM_DE5E_FLOORFALLXCOORD;
        int _RAM_DE60_;
        int _RAM_DE53_COMPASS;
        int _RAM_C800_1ST_METATILE_ROW=0xC800;
        int _RAM_FFFF_=0;
        Accumulator=(byte)roomNr;   //ld a,(_RAM_DE52_ROOM_NR)
    	HL=lvlPointer-2;            //ld hl,_DATA_1343_LVL_POINTERS - 2
        Accumulator+=Accumulator;   //add a,a
        E=Accumulator;              //ld e,a
        D=0;                        //ld d,$00
        DE = ((D & 0xFF) << 8) | (E & 0xFF);
        HL+=DE;                     //add hl, de
        Accumulator=rom[HL];        //ld a, (HL)
        HL++;                       //inc HL
        H=rom[HL];                  //ld h, (HL)
        L=Accumulator;
        HL = ((H & 0xFF) << 8) | (L & 0xFF);    //Combine to HL.
        Accumulator=rom[HL];                    //ld a, (HL)
        stack[stackpointer]=HL;                //Push HL
        stackpointer++;                         //Adjust stackpointer. 
        L=Accumulator;                         //ld l, a
        H=0;                                    //ld h, $00
        HL = ((H & 0xFF) << 8) | (L & 0xFF);    //Combine to HL.
        HL+=HL;         //add hl, hl
        HL+=HL;         //add hl, hl
        HL+=HL;         //add hl, hl
        HL+=HL;         //add hl, hl
        DE=0x0100;      //ld de,$0100
        Accumulator&=Accumulator;   //and a This is to lose carry, but I don't know if this is needed in java at least.
        HL-=DE;         //SBC HL,DE
        _RAM_DE3E_MAX_LVL_LENGHT=HL;   //LD (_RAM_DE3E_MAX_LVL_LEN), hl
        stack[stackpointer]=Accumulator;    //push AF   We don't need F here.
        stackpointer--; //Adjust SP.            //Some code comes now that adjusts the camera, but that's not needed here.
        HL=stack[stackpointer]; //Get back HL, A is still $50, so we are good on that.
        Accumulator=Accumulator>>2;     //SRL a, SRL a      We combine the two shifts here.
        B=Accumulator;      //ld b,a
        HL++;   //inc HL
        Accumulator=rom[HL];    //ld a, (hl)
        HL++;       //inc HL
        stack[stackpointer]=HL;     //push HL
        stackpointer++;
        HL=_DATA_12E1_;      //LD hl,_DATA_12E1_
        Accumulator+=Accumulator;   //ld a,a 
        E=Accumulator;      //ld e, a
        D=0;                //ld d,$00
        DE = ((D & 0xFF) << 8) | (E & 0xFF);
        HL+=DE;             //add HL, DE
        Accumulator=rom[HL];    //ld a, (HL)
        HL++;   //inc HL
        H=rom[HL];  //ld h, (hl)
        HL|=(H & 0xFF) << 8;    
        L=Accumulator;  //ld l, a
        HL = ((H & 0xFF) << 8) | (L & 0xFF);    //Combine to HL.
        Accumulator=rom[HL];    //a ,(HL)
        _RAM_DE31_METATILE_BANK=Accumulator;
        HL++;
        Accumulator=rom[HL];
        _RAM_DE2E_BANKSWITCH_LEVEL=Accumulator;    //ld (_RAM_DE31_METATILE_BANK), a	;Metatile bank.
        HL++;       //inc hl
        Accumulator=rom[HL];
        _RAM_DE29_METATILE_TILE_LOAD=Accumulator;   //ld (_RAM_DE29_METATILE_TILE_LOAD), a	;05	This is the tiles for the metatiles.
        HL++;       //inc HL
        E=rom[HL];  //ld e, (HL)
        HL++;           //inc HL
        D=rom[HL];      //ld d,(HL)
        DE = ((D & 0xFF) << 8) | (E & 0xFF);        //Set DE completely.
        _RAM_DE32_=rom[DE]; //ld (_RAM_DE32_),DE
        HL++;   //inc hl
        E=rom[HL];  //ld e,(HL)
        HL++;   //inc HL
        D=rom[HL];  //ld d,(HL)
        DE = ((D & 0xFF) << 8) | (E & 0xFF);        //Set DE completely.
        _RAM_DE2F_=DE;  //ld (_RAM_DE2F_), de
        HL++;
        E=rom[HL];  //ld e,(HL)
        HL++;   //inc HL
        D=rom[HL];  //ld d,(HL)
        DE = ((D & 0xFF) << 8) | (E & 0xFF);        //Set DE completely.
        _RAM_DE2A_=rom[DE]; //ld (_RAM_DE2A_), de	;$8000.	
        stackpointer--;
        HL=stack[stackpointer]; //pop hl
        E=rom[HL];  //ld e, (HL)
        HL++;   //inc HL
        D=rom[HL];  //d, (hl)
        DE = ((D & 0xFF) << 8) | (E & 0xFF);        //Set DE completely.
        HL++;
        stack[stackpointer]=DE; //push DE
        //stackpointer++; //We use this immediately.
        IX=DE;  //pop IX
        Accumulator=0;      //We skip the if, and during normal operation this will be zero.
        //Accumulator=roomNr; //ld a, (_RAM_DE52_ROOM_NR) //Not needed, as there was an if condition there, but that's not needed at the moment.
        //Here comes an if statement, though I'm not sure if it brings anything new here so far.
        E=rom[HL];  //ld e, (hl)
        HL++;   //inc hl
        D=rom[HL];  //ld d,(hl)
        DE = ((D & 0xFF) << 8) | (E & 0xFF);        //Set DE completely.
        HL++;   //inc HL
        _RAM_DE5E_FLOORFALLXCOORD=DE; //ld (_RAM_DE5E_FLOORFALLXCOORD), de
        E=rom[HL];  //ld e, (hl)
        HL++;   //inc hl
        D=rom[HL];  //ld d,(hl)
        DE = ((D & 0xFF) << 8) | (E & 0xFF);        //Set DE completely.
        HL++;   //inc HL
        _RAM_DE60_=DE;  //ld (_RAM_DE60_), de		;$1944.
        Accumulator=rom[HL]; //ld a, (hl)
        _RAM_DE53_COMPASS=Accumulator;  //
        int temp1,temp2=0;
        temp1=HL;
        temp2=sHL;
        HL=temp2;
        sHL=temp1;  //Exx HL.
        temp1=DE;
        temp2=sDE;
        DE=temp2;
        sDE=temp1;  //Exx DE.

        temp1=BC;
        temp2=sBC;
        BC=temp2;
        sBC=temp1;  //Exx BC.

        temp1=Accumulator;
        temp2=sAccumulator;
        Accumulator=temp2;
        sAccumulator=temp1;  //Exx Acc.

        DE=_RAM_C800_1ST_METATILE_ROW;  //This is C800 ld DE, $C800
        BC=4;   //ld BC, $0004

        temp1=HL;
        temp2=sHL;
        HL=temp2;
        sHL=temp1;  //Exx HL.
        temp1=DE;
        temp2=sDE;
        DE=temp2;
        sDE=temp1;  //Exx DE.

        temp1=BC;
        temp2=sBC;
        BC=temp2;
        sBC=temp1;  //Exx BC.

        temp1=Accumulator;
        temp2=sAccumulator;
        Accumulator=temp2;
        sAccumulator=temp1;  //Exx Acc.
        //An EXX back
        Accumulator=0;  //xor a
        int _RAM_DE5A_=Accumulator; //ld (_RAM_DE5A_), a
        int _RAM_DE59_LEFT_DEBUG_NR=Accumulator;    //ld (_RAM_DE59_LEFT_DEBUG_NR), a
//_LABEL_D29_
        stack[stackpointer]=BC; //push BC
        stackpointer++;
        temp1=HL;
        temp2=sHL;
        HL=temp2;
        sHL=temp1;  //Exx HL.
        temp1=DE;
        temp2=sDE;
        DE=temp2;
        sDE=temp1;  //Exx DE.

        temp1=BC;
        temp2=sBC;
        BC=temp2;
        sBC=temp1;  //Exx BC.

        temp1=Accumulator;
        temp2=sAccumulator;
        Accumulator=temp2;
        sAccumulator=temp1;  //Exx Acc.
        //This is a manual EXX. 
        HL=0x0004;  //ld hl, $0004
        HL+=DE; //add HL, DE
        temp1=HL;
        temp2=DE;
        HL=temp2;
        DE=temp1;   //ex hl,de

        temp1=HL;
        temp2=sHL;
        HL=temp2;
        sHL=temp1;  //Exx HL.
        temp1=DE;
        temp2=sDE;
        DE=temp2;
        sDE=temp1;  //Exx DE.

        temp1=BC;
        temp2=sBC;
        BC=temp2;
        sBC=temp1;  //Exx BC.

        temp1=Accumulator;
        temp2=sAccumulator;
        Accumulator=temp2;
        sAccumulator=temp1;  //Exx Acc.

        Accumulator=_RAM_DE2E_BANKSWITCH_LEVEL; //ld a, (_RAM_DE2E_BANKSWITCH_LEVEL)
        _RAM_FFFF_=Accumulator; //ld (_RAM_FFFF_), a    This is the bankswitch part of the code, but this also holds the last bank you've used, and some code refers to this, so this is definetly coming here.
        DE=_RAM_DE2F_;  //ld de, (_RAM_DE2F_)
        Accumulator=rom[IX+0];  //ld a, (IX+0) The +0 does nothing, but why not include it for completeness?
        IX++;   //inc IX
        //L=0;    //ld l,$00
        //H=Accumulator;  //ld h,a
        //HL = ((H & 0xFF) << 8) | (L & 0xFF);    //Combine to HL.
        //H>>=2;
        //HL>>=2; 
        //HL=emulateZ80Assembly(Accumulator);
        H = H >>> 1; // srl h
        L = (L >>> 1) | ((H & 0x1) << 7); // rr l

        H = H >>> 1; // srl h
        L = (L >>> 1) | ((H & 0x1) << 7); // rr l
        
/*
 * Upon analyzing the game code more, it seems that the shifting is a simple 16-bit one. H is shifted right, so if there's something in 
 * the last bit, it will be pushed into the carry flag.
 * We load L with zeroes, then shift the carry flag's contents into it, and then this is done for the second time.
 * This stuff above this is still cool as it is.
 */
        
        HL+=DE; //add HL, DE
        temp1=DE;
        temp2=HL;
        HL=temp1;
        DE=temp2;   //EX DE,HL
        B=8;    //ld B,$08
//_LABEL_D4F_:
        C=4;    //ld c, $04
        stack[stackpointer]=DE; //push DE
        stackpointer++;
//_LABEL_D52:
        BC = ((B & 0xFF) << 8) | (C & 0xFF);    //Combining the two.
        Accumulator=_RAM_DE2E_BANKSWITCH_LEVEL; //ld a, _RAM_DE2E_BANKSWITCH_LEVEL
        _RAM_FFFF_=Accumulator; //ld (_RAM_FFFF_),a
        Accumulator=DE; //ld a, (de)
        Accumulator+=0xD0;  //add a, $D0
        
        
        
        
        //System.out.println(Integer.toBinaryString(DE));
        System.out.println("Room Nr.: "+roomNr);
        System.out.println("DE: "+String.format("0x%02X", DE));
        System.out.println("HL: "+String.format("0x%02X", HL));
        System.out.println("IX: "+String.format("0x%02X", IX));
        System.out.println("Accumulator: "+String.format("0x%02X", Accumulator));
        System.out.println("Max. Level Length: "+String.format("0x%02X", _RAM_DE3E_MAX_LVL_LENGHT));

        System.out.println("_RAM_DE31_METATILE_BANK: "+String.format("0x%02X", _RAM_DE31_METATILE_BANK));
        System.out.println("_RAM_DE29_METATILE_TILE_LOAD: "+String.format("0x%02X", _RAM_DE29_METATILE_TILE_LOAD));
        System.out.println("_RAM_DE32_: "+String.format("0x%02X", _RAM_DE32_));
        System.out.println("_RAM_DE2F_: "+String.format("0x%02X", _RAM_DE2F_));
        
        System.out.println("RAM_DE2E_BANKSWITCH_LEVEL: "+String.format("0x%02X", _RAM_DE2E_BANKSWITCH_LEVEL));
        System.out.println("_RAM_DE2A_: "+String.format("0x%02X", _RAM_DE2A_));
        System.out.println("_RAM_DE5E_FLOORFALLXCOORD: "+String.format("0x%02X", _RAM_DE5E_FLOORFALLXCOORD));
        System.out.println("_RAM_DE60_: "+String.format("0x%02X", _RAM_DE60_));
        System.out.println("_RAM_DE53_COMPASS: "+String.format("0x%02X", _RAM_DE53_COMPASS));
        System.out.println("_RAM_DE60_: "+String.format("0x%02X", _RAM_DE60_));
        roomDetails.add(static_cast<String>(roomNr));
        //Okay, these details seem to be good for now to get some details about the maps. The map loading itself is anything but trivial, for me at least.

        //System.out.println(Integer.toBinaryString(lTemp<<2));
        //int HL,DE,BC,IX,IY=0;
        //int sHL,sDE,sBC,sAccumulator;
    }

}
