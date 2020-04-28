# FTC_IDK_VISION_LIB

***

### Description:

<i>Simple and Easy to Use FTC Vision Pipeline System Library, Created By Team #4653 Irrational DoorKeepers</i>

To see how it works visit Slide 7: https://docs.google.com/presentation/d/15Jl5zCvmG0FUKjGg9StxLcLe4oXixky9quh6ihowrpA/edit?usp=sharing

***

### Installation Instructions:

1. Open FTC project in <i>Android Studio</i> (no OnBot Java :[ )... <b>Gradle Scripts -> TeamCode-build.gradle -> add the lines</b>
   ```
   allprojects {
     repositories {
        maven { url 'https://jitpack.io' }
     }
   }
   
   dependencies {
     implementation 'com.github.FTCIrrationalDoorkeepers4653:FTC_IDK_VISION_LIB:Tag'
   }
   ```
   <b>-> Sync Gradle</b>
2. If <i>Jitpack</i> doesn't work, download this repo, extract, and go into <i>Android Studio</i>, add the repo as a module, go to <b>Gradle Scripts -> TeamCode-buld.release.gradle -> add the line</b> ```implementation project (':FTC_IDK_VISION_LIB')``` <b>-> Sync Gradle</b>
3. Lastly, add Source Files of <i>TeamCode-Files</i> to your <b>Team Code -> src -> main -> java -> org -> firstinspires -> ftc -> teamcode package</b>

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
