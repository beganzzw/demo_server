package org.gwhere.permission.mapper;

import org.gwhere.permission.model.SysEnroll;

public interface SysEnrollMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_enroll
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_enroll
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    int insert(SysEnroll record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_enroll
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    int insertSelective(SysEnroll record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_enroll
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    SysEnroll selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_enroll
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    int updateByPrimaryKeySelective(SysEnroll record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_enroll
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    int updateByPrimaryKey(SysEnroll record);
}