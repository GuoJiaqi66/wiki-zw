package com.zw.domain;

import java.util.Date;

public class EbookSnapshot {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ebook_snapshot.id
     *
     * @mbg.generated Fri Dec 03 14:10:53 CST 2021
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ebook_snapshot.ebook_id
     *
     * @mbg.generated Fri Dec 03 14:10:53 CST 2021
     */
    private Long ebookId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ebook_snapshot.date
     *
     * @mbg.generated Fri Dec 03 14:10:53 CST 2021
     */
    private Date date;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ebook_snapshot.view_count
     *
     * @mbg.generated Fri Dec 03 14:10:53 CST 2021
     */
    private Integer viewCount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ebook_snapshot.vote_count
     *
     * @mbg.generated Fri Dec 03 14:10:53 CST 2021
     */
    private Integer voteCount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ebook_snapshot.view_increase
     *
     * @mbg.generated Fri Dec 03 14:10:53 CST 2021
     */
    private Integer viewIncrease;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ebook_snapshot.vote_increase
     *
     * @mbg.generated Fri Dec 03 14:10:53 CST 2021
     */
    private Integer voteIncrease;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ebook_snapshot.id
     *
     * @return the value of ebook_snapshot.id
     *
     * @mbg.generated Fri Dec 03 14:10:53 CST 2021
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ebook_snapshot.id
     *
     * @param id the value for ebook_snapshot.id
     *
     * @mbg.generated Fri Dec 03 14:10:53 CST 2021
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ebook_snapshot.ebook_id
     *
     * @return the value of ebook_snapshot.ebook_id
     *
     * @mbg.generated Fri Dec 03 14:10:53 CST 2021
     */
    public Long getEbookId() {
        return ebookId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ebook_snapshot.ebook_id
     *
     * @param ebookId the value for ebook_snapshot.ebook_id
     *
     * @mbg.generated Fri Dec 03 14:10:53 CST 2021
     */
    public void setEbookId(Long ebookId) {
        this.ebookId = ebookId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ebook_snapshot.date
     *
     * @return the value of ebook_snapshot.date
     *
     * @mbg.generated Fri Dec 03 14:10:53 CST 2021
     */
    public Date getDate() {
        return date;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ebook_snapshot.date
     *
     * @param date the value for ebook_snapshot.date
     *
     * @mbg.generated Fri Dec 03 14:10:53 CST 2021
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ebook_snapshot.view_count
     *
     * @return the value of ebook_snapshot.view_count
     *
     * @mbg.generated Fri Dec 03 14:10:53 CST 2021
     */
    public Integer getViewCount() {
        return viewCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ebook_snapshot.view_count
     *
     * @param viewCount the value for ebook_snapshot.view_count
     *
     * @mbg.generated Fri Dec 03 14:10:53 CST 2021
     */
    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ebook_snapshot.vote_count
     *
     * @return the value of ebook_snapshot.vote_count
     *
     * @mbg.generated Fri Dec 03 14:10:53 CST 2021
     */
    public Integer getVoteCount() {
        return voteCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ebook_snapshot.vote_count
     *
     * @param voteCount the value for ebook_snapshot.vote_count
     *
     * @mbg.generated Fri Dec 03 14:10:53 CST 2021
     */
    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ebook_snapshot.view_increase
     *
     * @return the value of ebook_snapshot.view_increase
     *
     * @mbg.generated Fri Dec 03 14:10:53 CST 2021
     */
    public Integer getViewIncrease() {
        return viewIncrease;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ebook_snapshot.view_increase
     *
     * @param viewIncrease the value for ebook_snapshot.view_increase
     *
     * @mbg.generated Fri Dec 03 14:10:53 CST 2021
     */
    public void setViewIncrease(Integer viewIncrease) {
        this.viewIncrease = viewIncrease;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ebook_snapshot.vote_increase
     *
     * @return the value of ebook_snapshot.vote_increase
     *
     * @mbg.generated Fri Dec 03 14:10:53 CST 2021
     */
    public Integer getVoteIncrease() {
        return voteIncrease;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ebook_snapshot.vote_increase
     *
     * @param voteIncrease the value for ebook_snapshot.vote_increase
     *
     * @mbg.generated Fri Dec 03 14:10:53 CST 2021
     */
    public void setVoteIncrease(Integer voteIncrease) {
        this.voteIncrease = voteIncrease;
    }
}