package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.IdType.ASSIGN_ID;

/**
 * <p>
 * 
 * </p>
 *
 * @author author
 * @since 2024-10-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@Accessors(chain = true)
@TableName("checkitem_info")
@AllArgsConstructor
@NoArgsConstructor
public class CheckitemInfo implements Serializable {

    @TableId(type = ASSIGN_ID)
    private String checkitemId;

    private String checkitemName;

    private String checkId;

    @JsonFormat(pattern = "yyy/MM/dd HH:mm:ss", timezone="GMT+8")
    private LocalDateTime checkitemTime;

    private String checkitemOut;

    private String checkitemWay;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    @TableLogic(value = "0",delval = "1")
    private Integer isDeleted;

    @Override
    public boolean equals(Object that){
        if (this == that){
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()){
            return false;
        }
        CheckitemInfo other = (CheckitemInfo)that;
        return (this.getCheckId() == null ? other.getCheckId() == null : this.getCheckId().equals(other.getCheckId()))
                &&(getCheckitemName() == null ? other.getCheckitemName() == null : this.getCheckitemName().equals(other.getCheckitemName()))
                &&(getCheckId() == null ? other.getCheckId() == null : this.getCheckId().equals(other.getCheckId()))
                &&(getCheckitemTime() == null ? other.getCheckitemTime() == null : this.getCheckitemTime().equals(other.getCheckitemTime()))
                &&(getCheckitemOut() == null ? other.getCheckitemOut() == null : this.getCheckitemOut().equals(other.getCheckitemOut()))
                &&(getCheckitemWay() == null ? other.getCheckitemWay() == null : this.getCheckitemWay().equals(other.getCheckitemWay()));
    }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCheckitemId() ==null) ? 0 : getCheckitemId().hashCode());
        result = prime * result + ((getCheckitemName() ==null) ? 0 : getCheckitemName().hashCode());
        result = prime * result + ((getCheckId() ==null) ? 0 : getCheckId().hashCode());
        result = prime * result + ((getCheckitemTime() ==null) ? 0 : getCheckitemTime().hashCode());
        result = prime * result + ((getCheckitemOut() ==null) ? 0 : getCheckitemOut().hashCode());
        result = prime * result + ((getCheckitemWay() ==null) ? 0 : getCheckitemWay().hashCode());
        return result;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", checkitemId=").append(checkitemId);
        sb.append(", checkitemName=").append(checkitemName);
        sb.append(", checkid=").append(checkId);
        sb.append(", checkitemTime=").append(checkitemTime);
        sb.append(", checkitemOut=").append(checkitemOut);
        sb.append(", checkitemWay=").append(checkitemWay);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

}
