package com.leus.business;

public final class Area {

    private final Coordinate upperPixel;
    private final Coordinate mostLeftPixel;
    private final Coordinate mostRightPixel;
    private final Coordinate lowerPixel;

    public Area(final Coordinate upperPixel, final Coordinate mostLeftPixel, final Coordinate mostRightPixel, final Coordinate lowerPixel) {
        this.upperPixel = upperPixel;
        this.mostLeftPixel = mostLeftPixel;
        this.mostRightPixel = mostRightPixel;
        this.lowerPixel = lowerPixel;
    }

    public Coordinate getUpperPixel() {
        return upperPixel;
    }

    public Coordinate getMostLeftPixel() {
        return mostLeftPixel;
    }

    public Coordinate getMostRightPixel() {
        return mostRightPixel;
    }

    public Coordinate getLowerPixel() {
        return lowerPixel;
    }
}
