# Video Editor
Hi, welcome to the video editor, to run the program
it is important that you set your folder in the main class. By
default you could just add your images to the images folder.

The project will handle the media and output a video in the
temporary folder. This folder will be erased with every run
so please do not add sensitive data, and try to retrieve the
media you find useful for later.

The program uses exiftool, ffmpeg, and curl. Please feel free to use 
the build I already added on the project, for the ffmpeg build you 
may encounter a warning saying permission denied, so please use
chmod +x ./ffmpeg/ffmpeg from the project folder to make sure it works
correctly, please also install curl for the project to work as expected.