public final class ArrayTools {
    public static byte[][] explodeRGBChannels(byte[] rgb) {
        byte[][] explode = new byte[3][rgb.length / 3];
        int i = 0;
        int j = 0;
        for (; i < (rgb.length - 3); i += 3, ++j) {
            explode[0][j] = rgb[i];
            explode[1][j] = rgb[i + 1];
            explode[2][j] = rgb[i + 2];
        }

        explode[0][j] = rgb[i];
        explode[1][j] = rgb[i + 1];
        explode[2][j] = rgb[i + 2];
        return explode;
    }

    public static int[] mergeRGBChannels(byte[] rgb) {
        int[] merge = new int[rgb.length / 3];
        int i = 0;
        int j = 0;
        for (; i < (rgb.length - 3); i += 3, ++j) {
            merge[j] = (((int) rgb[i] & 0xFF) << 0x10 | ((int) rgb[i + 1] & 0xFF) << 0x08 | ((int) rgb[i + 2] & 0xFF));
        }

        merge[j] = (((int) rgb[i] & 0xFF) << 0x10 | ((int) rgb[i + 1] & 0xFF) << 0x08 | ((int) rgb[i + 2]) & 0xFF);
        return merge;
    }

    public static byte[] mergeRGBChannels(int[] r, int[] g, int[] b) {
        if (r.length != g.length || r.length != b.length || g.length != b.length) {
            return null;
        }

        byte[] merge = new byte[r.length * 3];
        int i = 0;
        int j = 0;
        for (; i < (r.length - 3); ++i, j += 3) {
            merge[j] = (byte) (r[i] & 0xFF);
            merge[j + 1] = (byte) (g[i] & 0xFF);
            merge[j + 2] = (byte) (b[i] & 0xFF);
        }

        merge[j] = (byte) (r[i] & 0xFF);
        merge[j + 1] = (byte) (g[i] & 0xFF);
        merge[j + 2] = (byte) (b[i] & 0xFF);
        return merge;
    }
}
