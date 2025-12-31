package com.ncg233.tutorialmod.bibliocraftest.helper;

import com.ncg233.tutorialmod.BiblioCraft;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PaintingUtil {

    public static int[] test;
    public static String[] customArtNames = null;
    public static int[] customArtHeights = null;
    public static int[] customArtWidths = null;
    public static ResourceLocation[] customArtResources = null;
    public static String[] customArtResourceStrings = null;

    public static void updateCustomArtDatas()
    {
        PaintingUtil.customArtNames = getListOfPaintings();
        if (PaintingUtil.customArtNames != null)
        {
            PaintingUtil.customArtResources = getPaintingsResourceLocations(customArtNames);
            PaintingUtil.customArtResourceStrings = getPaintingsStringLocations(customArtNames);
            PaintingUtil.customArtHeights = getPaintingSetHeights(customArtNames, customArtResources);
            PaintingUtil.customArtWidths = getPaintingSetWidths(customArtNames, customArtResources);
        }
    }

    private static ResourceLocation[] getPaintingsResourceLocations(String[] names)
    {
        ResourceLocation[] paintings = new ResourceLocation[names.length];
        for (int i = 0; i < names.length; i++)
        {
            paintings[i] = ResourceLocation.fromNamespaceAndPath(BiblioCraft.MODID,"textures/custompaintings/"+names[i]);
        }
        return paintings;
    }

    private static String[] getPaintingsStringLocations(String[] names)
    {
        String[] paintings = new String[names.length];
        for (int i = 0; i < names.length; i++)
        {
            paintings[i] = ""+BiblioCraft.MODID+":custompaintings/"+names[i];
            paintings[i] = paintings[i].replace(".png", "");
        }
        return paintings;
    }

    private static int[] getPaintingSetHeights(String[] paintings, ResourceLocation[] resources)
    {
        int[] heights = new int[paintings.length];
        ResourceManager rm = Minecraft.getInstance().getResourceManager();
        try
        {
            for (int i = 0; i < paintings.length; i++)
            {
                Optional<Resource> resourceOptional = rm.getResource(resources[i]);
                if (resourceOptional.isPresent())
                {   Resource theThing = resourceOptional.get();

                    Image img = ImageIO.read(theThing.open());
                    if (img != null)
                    {
                        heights[i] = img.getHeight(null);
                    }
                }
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return heights;
    }

    private static int[] getPaintingSetWidths(String[] paintings, ResourceLocation[] resources)
    {
        int[] widths = new int[paintings.length];
        ResourceManager rm = Minecraft.getInstance().getResourceManager();
        try
        {
            for (int i = 0; i < paintings.length; i++)
            {
                Optional<Resource> resourceOptional = rm.getResource(resources[i]);
                if (resourceOptional.isPresent())
                {   Resource theThing = resourceOptional.get();
                    Image img = ImageIO.read(theThing.open());
                    if (img != null)
                    {
                        widths[i] = img.getWidth(null);
                    }
                }
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return widths;
    }

    private static String[] getListOfPaintings()
    {
        String[] resources = getJarPaintings();
        String[] resourcePacks =null;

        if (resources != null && resources.length > 0 && resourcePacks != null && resourcePacks.length > 0)
        {
            String[] masterList = new String[resources.length+resourcePacks.length];
            int indexAdjuster = 0;
            for (int i = 0; i < masterList.length; i++)
            {
                if (i < resources.length)
                {
                    masterList[i] = resources[i];
                }
                else
                {
                    masterList[i] = resourcePacks[i-resources.length];
                }
            }
            return masterList;
        }
        else if ((resources == null || resources.length < 1) && resourcePacks != null && resourcePacks.length > 0)
        {
            return resourcePacks;
        }
        else if (resources != null && resources.length > 0 && (resourcePacks == null || resourcePacks.length < 1))
        {
            return resources;
        }
        return null;
    }

    @SuppressWarnings("resource")
    private static String[] getJarPaintings()
    {
        String path = "assets/"+BiblioCraft.MODID+"/textures/custompaintings/";
        URL dirURL = BiblioCraft.instance.getClass().getResource("/assets/"+BiblioCraft.MODID+"/textures/custompaintings/");
        if (dirURL != null && dirURL.getProtocol().equals("file"))
        {
            try
            {
                String[] newSet = new File(dirURL.toURI()).list();
                int setSize = 0;
                ArrayList<String> thePNGs = new ArrayList<String>();
                for (int i = 0; i < newSet.length; i++)
                {
                    if (newSet[i].contains(".png"))
                    {
                        thePNGs.add(newSet[i]);
                    }
                }
                String[] finalSet = new String[thePNGs.size()];

                for (int i = 0; i < thePNGs.size(); i++)
                {
                    finalSet[i] = thePNGs.get(i);
                }
                return finalSet;
            }
            catch (URISyntaxException e)
            {
                e.printStackTrace();
            }
        }
        if (dirURL != null && dirURL.getProtocol().equals("jar"))
        {
            String jarPath = dirURL.getPath().substring(5, dirURL.getPath().indexOf("!"));
            JarFile jar;
            try
            {
                jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
                Enumeration<JarEntry> entries = jar.entries(); 
                Set<String> result = new HashSet<String>(); 
                while(entries.hasMoreElements())
                {
                    String name = entries.nextElement().getName();
                    if (name.startsWith(path)) 
                    {

                        String entry = name.substring(path.length());
                        
                        if (entry.contains(".png"))
                        {
                            
                            result.add(entry);
                        }
                    }
                }
                return result.toArray(new String[result.size()]);

            }
            catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return null;
    }

}
