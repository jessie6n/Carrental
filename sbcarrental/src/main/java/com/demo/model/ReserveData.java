package com.demo.model;

public class ReserveData {

    private Integer carId;
    private boolean insurance;
    private int childSeatQty;

    // Getter / Setter
    public Integer getCarId() {
        return carId;
    }
    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public boolean isInsurance() {
        return insurance;
    }
    public void setInsurance(boolean insurance) {
        this.insurance = insurance;
    }

    public int getChildSeatQty() {
        return childSeatQty;
    }
    public void setChildSeatQty(int childSeatQty) {
        this.childSeatQty = childSeatQty;
    }
}
