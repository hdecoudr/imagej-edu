public final class PixelTools {
    public static int getAlpha(int col) {
        return (col >> 0x18);
    }

    public static int getRed(int col) {
        return ((col >> 0x10) & 0xFF);
    }

    public static int getGreen(int col) {
        return ((col >> 0x08) & 0xFF);
    }

    public static int getBlue(int col) {
        return (col & 0xFF);
    }

    public static int pixelToGrayScale(int pixel) {
        double pR, pG, pB;
        pR = (double) ((pixel >> 0x10) & 0xFF);
        pG = (double) ((pixel >> 0x08) & 0xFF);
        pB = (double) (pixel & 0xFF);
        pR *= 0.30;
        pG *= 0.59;
        pB *= 0.11;
        pR = pR + pG + pB;
        int gray = pixel & 0xFF000000;
        gray |= ((int) pR << 0x10);
        gray |= ((int) pR << 0x08);
        gray |= ((int) pR);
        return gray;
    }

    public static double deltaCIE76Distance(int col1, int col2) {
        return Math.sqrt(
                Math.pow(com.im.td3.PixelTools.getRed(col1) - com.im.td3.PixelTools.getRed(col2), 2) +
                        Math.pow(com.im.td3.PixelTools.getGreen(col1) - com.im.td3.PixelTools.getGreen(col2), 2) +
                        Math.pow(com.im.td3.PixelTools.getBlue(col1) - com.im.td3.PixelTools.getBlue(col2), 2));
    }

    public static int deltaCIE76DistanceRGB(int col1, int col2) {
        int color = com.im.td3.PixelTools.deltaCIE76DistanceRed(col1, col2) << 0x10;
        color |= com.im.td3.PixelTools.deltaCIE76DistanceGreen(col1, col2) << 0x08;
        color |= com.im.td3.PixelTools.deltaCIE76DistanceBlue(col1, col2);
        return color;
    }

    public static int deltaCIE76DistanceRed(int col1, int col2) {
        return (int) Math.sqrt(Math.pow(com.im.td3.PixelTools.getRed(col1) - com.im.td3.PixelTools.getRed(col2), 2));
    }

    public static int deltaCIE76DistanceGreen(int col1, int col2) {
        return (int) Math.sqrt(Math.pow(com.im.td3.PixelTools.getGreen(col1) - com.im.td3.PixelTools.getGreen(col2), 2));
    }

    public static int deltaCIE76DistanceBlue(int col1, int col2) {
        return (int) Math.sqrt(Math.pow(com.im.td3.PixelTools.getBlue(col1) - com.im.td3.PixelTools.getBlue(col2), 2));
    }
}
