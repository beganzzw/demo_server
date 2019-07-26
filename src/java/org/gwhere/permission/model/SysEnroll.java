package org.gwhere.permission.model;

import java.util.Date;

public class SysEnroll {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_enroll.ID
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_enroll.STUDENT_ID
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    private Integer studentId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_enroll.CLASS_ID
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    private Integer classId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_enroll.MARK
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    private Float mark;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_enroll.STATUS
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    private Integer status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_enroll.CREATE_TIME
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_enroll.CREATE_USER
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    private String createUser;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_enroll.LAST_UPDATE_TIME
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    private Date lastUpdateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_enroll.LAST_UPDATE_USER
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    private String lastUpdateUser;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_enroll.ID
     *
     * @return the value of sys_enroll.ID
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_enroll.ID
     *
     * @param id the value for sys_enroll.ID
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_enroll.STUDENT_ID
     *
     * @return the value of sys_enroll.STUDENT_ID
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public Integer getStudentId() {
        return studentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_enroll.STUDENT_ID
     *
     * @param studentId the value for sys_enroll.STUDENT_ID
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_enroll.CLASS_ID
     *
     * @return the value of sys_enroll.CLASS_ID
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public Integer getClassId() {
        return classId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_enroll.CLASS_ID
     *
     * @param classId the value for sys_enroll.CLASS_ID
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_enroll.MARK
     *
     * @return the value of sys_enroll.MARK
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public Float getMark() {
        return mark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_enroll.MARK
     *
     * @param mark the value for sys_enroll.MARK
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public void setMark(Float mark) {
        this.mark = mark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_enroll.STATUS
     *
     * @return the value of sys_enroll.STATUS
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_enroll.STATUS
     *
     * @param status the value for sys_enroll.STATUS
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_enroll.CREATE_TIME
     *
     * @return the value of sys_enroll.CREATE_TIME
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_enroll.CREATE_TIME
     *
     * @param createTime the value for sys_enroll.CREATE_TIME
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_enroll.CREATE_USER
     *
     * @return the value of sys_enroll.CREATE_USER
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_enroll.CREATE_USER
     *
     * @param createUser the value for sys_enroll.CREATE_USER
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_enroll.LAST_UPDATE_TIME
     *
     * @return the value of sys_enroll.LAST_UPDATE_TIME
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_enroll.LAST_UPDATE_TIME
     *
     * @param lastUpdateTime the value for sys_enroll.LAST_UPDATE_TIME
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_enroll.LAST_UPDATE_USER
     *
     * @return the value of sys_enroll.LAST_UPDATE_USER
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_enroll.LAST_UPDATE_USER
     *
     * @param lastUpdateUser the value for sys_enroll.LAST_UPDATE_USER
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser == null ? null : lastUpdateUser.trim();
    }
}