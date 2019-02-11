# Steganograph!
Hide your text in an image!
# To run the encoder:
java -jar encode.jar \<random seed\> \<input image path or url\> \<string to encode\> \<output path\>  
* internet connection is required to load an image from URL  
* the same seed should be used in the decoder
# To run the decoder:
java -jar decode.jar \<seed\> \<original file path or url\> \<path or url to the image with hidden string\>  
* internet connection is required to load an image from URL  
* output to console  
