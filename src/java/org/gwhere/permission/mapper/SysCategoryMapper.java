package org.gwhere.permission.mapper;

import org.gwhere.permission.model.SysCategory;

public interface SysCategoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_category
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_category
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    int insert(SysCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_category
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    int insertSelective(SysCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_category
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    SysCategory selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_category
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    int updateByPrimaryKeySelective(SysCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_category
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    int updateByPrimaryKey(SysCategory record);
}