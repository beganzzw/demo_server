package org.gwhere.permission.model;

import java.util.Date;

public class SysStudent {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_student.ID
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_student.STUDENT_NUMBER
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    private String studentNumber;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_student.STUDENT_NAME
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    private String studentName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_student.STUDENT_ADDRESS
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    private String studentAddress;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_student.STUDENT_IPHONE
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    private Long studentIphone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_student.STATUS
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    private Integer status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_student.CREATE_TIME
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_student.CREATE_USER
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    private String createUser;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_student.LAST_UPDATE_TIME
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    private Date lastUpdateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_student.LAST_UPDATE_USER
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    private String lastUpdateUser;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_student.ID
     *
     * @return the value of sys_student.ID
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_student.ID
     *
     * @param id the value for sys_student.ID
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_student.STUDENT_NUMBER
     *
     * @return the value of sys_student.STUDENT_NUMBER
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public String getStudentNumber() {
        return studentNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_student.STUDENT_NUMBER
     *
     * @param studentNumber the value for sys_student.STUDENT_NUMBER
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber == null ? null : studentNumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_student.STUDENT_NAME
     *
     * @return the value of sys_student.STUDENT_NAME
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_student.STUDENT_NAME
     *
     * @param studentName the value for sys_student.STUDENT_NAME
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName == null ? null : studentName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_student.STUDENT_ADDRESS
     *
     * @return the value of sys_student.STUDENT_ADDRESS
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public String getStudentAddress() {
        return studentAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_student.STUDENT_ADDRESS
     *
     * @param studentAddress the value for sys_student.STUDENT_ADDRESS
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public void setStudentAddress(String studentAddress) {
        this.studentAddress = studentAddress == null ? null : studentAddress.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_student.STUDENT_IPHONE
     *
     * @return the value of sys_student.STUDENT_IPHONE
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public Long getStudentIphone() {
        return studentIphone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_student.STUDENT_IPHONE
     *
     * @param studentIphone the value for sys_student.STUDENT_IPHONE
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public void setStudentIphone(Long studentIphone) {
        this.studentIphone = studentIphone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_student.STATUS
     *
     * @return the value of sys_student.STATUS
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_student.STATUS
     *
     * @param status the value for sys_student.STATUS
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_student.CREATE_TIME
     *
     * @return the value of sys_student.CREATE_TIME
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_student.CREATE_TIME
     *
     * @param createTime the value for sys_student.CREATE_TIME
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_student.CREATE_USER
     *
     * @return the value of sys_student.CREATE_USER
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_student.CREATE_USER
     *
     * @param createUser the value for sys_student.CREATE_USER
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_student.LAST_UPDATE_TIME
     *
     * @return the value of sys_student.LAST_UPDATE_TIME
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_student.LAST_UPDATE_TIME
     *
     * @param lastUpdateTime the value for sys_student.LAST_UPDATE_TIME
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_student.LAST_UPDATE_USER
     *
     * @return the value of sys_student.LAST_UPDATE_USER
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_student.LAST_UPDATE_USER
     *
     * @param lastUpdateUser the value for sys_student.LAST_UPDATE_USER
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser == null ? null : lastUpdateUser.trim();
    }
}