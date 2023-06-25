# FfmpegEditor
Hi, welcome to the ffmpeg video editor, to run the program
it is important that you set your folder in the main class. By
default add your images to an "images" folder in the src folder
of the project.

The project will handle the media and output a video in the
temporary folder. This folder will be erased with every run
so please do not add sensitive data, and try to retrieve the
media you find useful for later.

The program uses exiftool, ffmpeg, and curl. Please add the 
libraries and correct the paths as needed. For ffmpeg, please
use a recent version as there may be some mistakes in rotation
if you use an older version.
