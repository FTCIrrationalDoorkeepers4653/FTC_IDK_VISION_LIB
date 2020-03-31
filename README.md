# FTC_IDK_VISION_LIB

***

### Description:

<i>Simple and Easy to Use FTC Vision Pipeline System Library, Created By Team #4653 Irrational DoorKeepers</i>

To see how it works visit Slide 7: https://docs.google.com/presentation/d/15Jl5zCvmG0FUKjGg9StxLcLe4oXixky9quh6ihowrpA/edit?usp=sharing

***

### Installation Instructions:

1. Download the zip…Extract All to a chosen Location
2. Open FTC project in <i>Android Studio</i> (no OnBot Java :[ )... <b>File -> New -> Import Module -> FTC_IDK_VISION_LIB</b> (Extracted Location)
3. In <i>Android Studio</i>, go to <b>Gradle Scripts -> TeamCode-buld.release.gradle -> add the line ```implements project (':FTC_IDK_VISION_LIB')``` -> Sync Gradle</b>
4. Lastly, add Source Files of <i>TeamCode-Files</i> to your <b>Team Code -> src -> main -> java -> org -> firstinspires -> ftc -> teamcode</b> package

***
   
### Usage Instructions:
  
Sample Pipeline:
```Java
  
 public boolean sampleLeftPipeline() {
   int lightingArray[] = { 25, 25, 25 };
   Bitmap objectImage = VuforiaImageInit.getImage(0.1);
   int leftRGBArray[][] = VuforiaImageInit.getRGBArray(objectImage, 12, 20, 20, 16);
   boolean foundLeft = imageInit.detectObject(leftRGBArray, lightingArray, 20);
   return foundLeft;
 }

```
