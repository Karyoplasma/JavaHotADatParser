# HotA.dat Parser

This project is a HotA.dat parser written in Java. It can export the entries into separate, editable files and merge them together when creating a new .dat file.

## Usage

**This program requires Java 8 or newer.**

Double-clicking the .jar file opens a basic gui where you can edit the entries in the HotA.dat on the fly. If you wish to rather use it as a command-line tool, place the HotA.dat you want as you base file into the res folder and run it with `java -jar HDATParser.jar <option> [option-args]`. The options are:

+ **-E**
    + exports all files found into export/files/ and creates a FileList.txt 
+ **-e "list, of, files"**
    + exports the list of files passed into export/ModFiles/ and creates a ModList.txt
+ **-W**
    + writes a new .dat file containing all files in the FileList.txt to export/output.dat
+ **-m** or **-w**
	+ merges the entries in the HotA.dat with the files listed in ModList.txt and writes a new .dat file to export/output.dat
	
The HotA.dat file will not be modified. Sample .bat files are provided in the [release.](https://github.com/Karyoplasma/JavaHotADatParser/releases/latest) 

## Credits

This project was inspired by [sake12/HotA-editor](https://github.com/sake12/HotA-editor).

## Link to latest release

https://github.com/Karyoplasma/JavaHotADatParser/releases/latest

## License

[GNU GPL 3.0](LICENSE)
