package org.gwhere.permission.mapper;

import org.gwhere.permission.model.SysCategoryItem;

public interface SysCategoryItemMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_category_item
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_category_item
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    int insert(SysCategoryItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_category_item
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    int insertSelective(SysCategoryItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_category_item
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    SysCategoryItem selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_category_item
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    int updateByPrimaryKeySelective(SysCategoryItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_category_item
     *
     * @mbg.generated Thu Jul 25 23:11:17 CST 2019
     */
    int updateByPrimaryKey(SysCategoryItem record);
}