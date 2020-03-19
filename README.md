# FTC_IDK_VISION_LIB
Simple and Easy to Use FTC Vision Pipeline System Library

To see how it works visit Slide 7: https://docs.google.com/presentation/d/15Jl5zCvmG0FUKjGg9StxLcLe4oXixky9quh6ihowrpA/edit?usp=sharing

### Installation Instructions:

1. Download the zip…Extract All to a chosen Location
2. Open FTC project in Android Studio (no OnBot Java :[ )... <b>File -> New -> Import Module -> FTC_IDK_VISION_LIB</b>
3. Add <b>src -> main -> java -> lib</b> Package of FTC_IDK_VISION_LIB to the Newly Created Module
4. Add Source Package of TeamCode-Files to your <b>Team Code -> src -> main -> java -> org -> firstinspires -> ftc -> teamcode</b> package
5. In Android Studio, go to <b>Gradle Scripts -> TeamCode-buld.release.gradle -> add the line ```implements project (':nameOfModule')``` -> Sync Gradle</b>
   
### Usage Instructions:
  
Sample Pipeline:
  ```Java
  public boolean sampleLeftPipeline() {
    //Gets RGB Array For Analysis-PARAMS: (Resize Ratio, startX, startY, width of area to analyze, height of area to analyze)
    int leftRGBArray[][] = imageInit.getRGBArray(0.1, 12, 20, 40, 32);
    
    //Analyzes Selected Portion of Image (using RGB above)-PARAMS: (rgbValues 2D Array, pixelMargin for lighting, number of pixels  counted before classified)
    boolean foundLeft = imageInit.detectObject(leftRGBArray, 25, 10);

    //Returning the Value:
    return foundLeft;
  }
  ```
  
See CustomVuforia_Test.java in TeamCode-Files, for more information.
