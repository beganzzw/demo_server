package org.gwhere.permission.model;

import java.util.Date;

public class SysClass {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_class.ID
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_class.TEACHER_ID
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    private Integer teacherId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_class.CLASS_NAME
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    private String className;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_class.STATUS
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    private Integer status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_class.CREATE_TIME
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_class.CREATE_USER
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    private String createUser;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_class.LAST_UPDATE_TIME
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    private Date lastUpdateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_class.LAST_UPDATE_USER
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    private String lastUpdateUser;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_class.ID
     *
     * @return the value of sys_class.ID
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_class.ID
     *
     * @param id the value for sys_class.ID
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_class.TEACHER_ID
     *
     * @return the value of sys_class.TEACHER_ID
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public Integer getTeacherId() {
        return teacherId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_class.TEACHER_ID
     *
     * @param teacherId the value for sys_class.TEACHER_ID
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_class.CLASS_NAME
     *
     * @return the value of sys_class.CLASS_NAME
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public String getClassName() {
        return className;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_class.CLASS_NAME
     *
     * @param className the value for sys_class.CLASS_NAME
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public void setClassName(String className) {
        this.className = className == null ? null : className.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_class.STATUS
     *
     * @return the value of sys_class.STATUS
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_class.STATUS
     *
     * @param status the value for sys_class.STATUS
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_class.CREATE_TIME
     *
     * @return the value of sys_class.CREATE_TIME
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_class.CREATE_TIME
     *
     * @param createTime the value for sys_class.CREATE_TIME
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_class.CREATE_USER
     *
     * @return the value of sys_class.CREATE_USER
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_class.CREATE_USER
     *
     * @param createUser the value for sys_class.CREATE_USER
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_class.LAST_UPDATE_TIME
     *
     * @return the value of sys_class.LAST_UPDATE_TIME
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_class.LAST_UPDATE_TIME
     *
     * @param lastUpdateTime the value for sys_class.LAST_UPDATE_TIME
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_class.LAST_UPDATE_USER
     *
     * @return the value of sys_class.LAST_UPDATE_USER
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_class.LAST_UPDATE_USER
     *
     * @param lastUpdateUser the value for sys_class.LAST_UPDATE_USER
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser == null ? null : lastUpdateUser.trim();
    }
}