import edu.duke.*;
import java.io.*;

public class HidePictures 
{
    
    private int changePixel(int pixval)
    {
        int x = (pixval/4) * 4;
        return x;
    }
   
    private ImageResource chop2hide(ImageResource image)
    {

        for(Pixel px : image.pixels())
        {
            px.setRed(changePixel(px.getRed()));
            px.setGreen(changePixel(px.getGreen()));
            px.setBlue(changePixel(px.getBlue()));
        }
        //image.draw();
        return image;        
    }
    
    public void testchop2Hide ()
    {
        DirectoryResource dr = new DirectoryResource();
        for(File f : dr.selectedFiles()){
            ImageResource im = new  ImageResource(f);
            chop2hide(im).draw();
        }
    }
   
    private void testshift ()
    {
        DirectoryResource dr = new DirectoryResource();
        for(File f : dr.selectedFiles()){
            ImageResource im = new  ImageResource(f);
            shift(im).draw();
        }

    }
    
    private ImageResource shift(ImageResource im)
    {
      ImageResource nim = new ImageResource(im.getWidth(),im.getHeight());
        
      for(Pixel px : im.pixels())
      {
        int x = px.getX();
        int y = px.getY();
        Pixel npx = nim.getPixel(x,y);
        
        npx.setRed(px.getRed()/32);
        npx.setGreen(px.getGreen()/32);
        npx.setBlue(px.getBlue()/32);
      }
      //nim.draw();
      return nim;
      
    }
    
    private ImageResource combine(ImageResource imstart,ImageResource imhide)
    {
         int height=imstart.getHeight();
         int width=imstart.getWidth();
         
         int hHeight= imhide.getHeight();
         int hWidth = imhide.getWidth();
         ImageResource res= new ImageResource(imstart.getWidth(),imstart.getHeight());

         for ( int i=0;i<width;i++)
         {
            for(int j=0;j<height;j++)
            {
                Pixel pix = res.getPixel(i, j);
                
                int hideRed = (j<hHeight && i<hWidth)?imhide.getPixel(i,j).getRed():0;
                int hideBlue = (j<hHeight && i<hWidth)?imhide.getPixel(i,j).getBlue():0;
                int hideGreen = (j<hHeight && i<hWidth)?imhide.getPixel(i,j).getGreen():0;
                
                int red=imstart.getPixel(i,j).getRed()+ hideRed;
                int blue=imstart.getPixel(i,j).getBlue()+ hideBlue;
                int green = imstart.getPixel(i,j).getGreen()+ hideGreen;
                
                pix.setRed(red);
                pix.setBlue(blue);
                pix.setGreen(green);
                
            }
         }

         return(res);
    }
    
    public void hideImages()
    {
        
        ImageResource start = new ImageResource();

        ImageResource hide = new ImageResource();

        // to make space for the image being hidden we will cut the pixels
        start = chop2hide(start);
        
        // to make the image being hidden less traceable we will shift the RGB values of the pixels.
        hide = shift(hide);
        
        // to combine the two altered images 
        ImageResource stegno = combine(start,hide);
        
        stegno.draw();
    }
}
