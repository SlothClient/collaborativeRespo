package com.example.springboot.entity;

import java.util.List;

public class VisualInfo<T> {
    private int totalOrder;
    private int toSendOrder;
    private int toFinishOrder;
    private int finishOrder;
    private String equipId;
    private String equipName;
    private int planCount;
    private String planType;
    private String typeCount;
    private String workerId;
    private String workerName;
    private int workerOrds;
    private double workerHours;

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public int getWorkerOrds() {
        return workerOrds;
    }

    public void setWorkerOrds(int workerOrds) {
        this.workerOrds = workerOrds;
    }

    public double getWorkerHours() {
        return workerHours;
    }

    public void setWorkerHours(double workerHours) {
        this.workerHours = workerHours;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getTypeCount() {
        return typeCount;
    }

    public void setTypeCount(String typeCount) {
        this.typeCount = typeCount;
    }

    private List<T> list;

    public String getEquipId() {
        return equipId;
    }

    public void setEquipId(String equipId) {
        this.equipId = equipId;
    }

    public String getEquipName() {
        return equipName;
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName;
    }

    public int getPlanCount() {
        return planCount;
    }

    public void setPlanCount(int planCount) {
        this.planCount = planCount;
    }

    public int getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(int totalOrder) {
        this.totalOrder = totalOrder;
    }

    public int getToSendOrder() {
        return toSendOrder;
    }

    public void setToSendOrder(int toSendOrder) {
        this.toSendOrder = toSendOrder;
    }

    public int getToFinishOrder() {
        return toFinishOrder;
    }

    public void setToFinishOrder(int toFinishOrder) {
        this.toFinishOrder = toFinishOrder;
    }

    public int getFinishOrder() {
        return finishOrder;
    }

    public void setFinishOrder(int finishOrder) {
        this.finishOrder = finishOrder;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
