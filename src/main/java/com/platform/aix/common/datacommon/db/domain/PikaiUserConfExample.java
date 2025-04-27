package com.platform.aix.common.datacommon.db.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PikaiUserConfExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PikaiUserConfExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(String value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(String value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(String value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(String value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(String value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLike(String value) {
            addCriterion("user_id like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotLike(String value) {
            addCriterion("user_id not like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<String> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<String> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(String value1, String value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(String value1, String value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andConfItemCodeIsNull() {
            addCriterion("conf_item_code is null");
            return (Criteria) this;
        }

        public Criteria andConfItemCodeIsNotNull() {
            addCriterion("conf_item_code is not null");
            return (Criteria) this;
        }

        public Criteria andConfItemCodeEqualTo(String value) {
            addCriterion("conf_item_code =", value, "confItemCode");
            return (Criteria) this;
        }

        public Criteria andConfItemCodeNotEqualTo(String value) {
            addCriterion("conf_item_code <>", value, "confItemCode");
            return (Criteria) this;
        }

        public Criteria andConfItemCodeGreaterThan(String value) {
            addCriterion("conf_item_code >", value, "confItemCode");
            return (Criteria) this;
        }

        public Criteria andConfItemCodeGreaterThanOrEqualTo(String value) {
            addCriterion("conf_item_code >=", value, "confItemCode");
            return (Criteria) this;
        }

        public Criteria andConfItemCodeLessThan(String value) {
            addCriterion("conf_item_code <", value, "confItemCode");
            return (Criteria) this;
        }

        public Criteria andConfItemCodeLessThanOrEqualTo(String value) {
            addCriterion("conf_item_code <=", value, "confItemCode");
            return (Criteria) this;
        }

        public Criteria andConfItemCodeLike(String value) {
            addCriterion("conf_item_code like", value, "confItemCode");
            return (Criteria) this;
        }

        public Criteria andConfItemCodeNotLike(String value) {
            addCriterion("conf_item_code not like", value, "confItemCode");
            return (Criteria) this;
        }

        public Criteria andConfItemCodeIn(List<String> values) {
            addCriterion("conf_item_code in", values, "confItemCode");
            return (Criteria) this;
        }

        public Criteria andConfItemCodeNotIn(List<String> values) {
            addCriterion("conf_item_code not in", values, "confItemCode");
            return (Criteria) this;
        }

        public Criteria andConfItemCodeBetween(String value1, String value2) {
            addCriterion("conf_item_code between", value1, value2, "confItemCode");
            return (Criteria) this;
        }

        public Criteria andConfItemCodeNotBetween(String value1, String value2) {
            addCriterion("conf_item_code not between", value1, value2, "confItemCode");
            return (Criteria) this;
        }

        public Criteria andConfItemNameIsNull() {
            addCriterion("conf_item_name is null");
            return (Criteria) this;
        }

        public Criteria andConfItemNameIsNotNull() {
            addCriterion("conf_item_name is not null");
            return (Criteria) this;
        }

        public Criteria andConfItemNameEqualTo(String value) {
            addCriterion("conf_item_name =", value, "confItemName");
            return (Criteria) this;
        }

        public Criteria andConfItemNameNotEqualTo(String value) {
            addCriterion("conf_item_name <>", value, "confItemName");
            return (Criteria) this;
        }

        public Criteria andConfItemNameGreaterThan(String value) {
            addCriterion("conf_item_name >", value, "confItemName");
            return (Criteria) this;
        }

        public Criteria andConfItemNameGreaterThanOrEqualTo(String value) {
            addCriterion("conf_item_name >=", value, "confItemName");
            return (Criteria) this;
        }

        public Criteria andConfItemNameLessThan(String value) {
            addCriterion("conf_item_name <", value, "confItemName");
            return (Criteria) this;
        }

        public Criteria andConfItemNameLessThanOrEqualTo(String value) {
            addCriterion("conf_item_name <=", value, "confItemName");
            return (Criteria) this;
        }

        public Criteria andConfItemNameLike(String value) {
            addCriterion("conf_item_name like", value, "confItemName");
            return (Criteria) this;
        }

        public Criteria andConfItemNameNotLike(String value) {
            addCriterion("conf_item_name not like", value, "confItemName");
            return (Criteria) this;
        }

        public Criteria andConfItemNameIn(List<String> values) {
            addCriterion("conf_item_name in", values, "confItemName");
            return (Criteria) this;
        }

        public Criteria andConfItemNameNotIn(List<String> values) {
            addCriterion("conf_item_name not in", values, "confItemName");
            return (Criteria) this;
        }

        public Criteria andConfItemNameBetween(String value1, String value2) {
            addCriterion("conf_item_name between", value1, value2, "confItemName");
            return (Criteria) this;
        }

        public Criteria andConfItemNameNotBetween(String value1, String value2) {
            addCriterion("conf_item_name not between", value1, value2, "confItemName");
            return (Criteria) this;
        }

        public Criteria andConfItemValueIsNull() {
            addCriterion("conf_item_value is null");
            return (Criteria) this;
        }

        public Criteria andConfItemValueIsNotNull() {
            addCriterion("conf_item_value is not null");
            return (Criteria) this;
        }

        public Criteria andConfItemValueEqualTo(Object value) {
            addCriterion("conf_item_value =", value, "confItemValue");
            return (Criteria) this;
        }

        public Criteria andConfItemValueNotEqualTo(Object value) {
            addCriterion("conf_item_value <>", value, "confItemValue");
            return (Criteria) this;
        }

        public Criteria andConfItemValueGreaterThan(Object value) {
            addCriterion("conf_item_value >", value, "confItemValue");
            return (Criteria) this;
        }

        public Criteria andConfItemValueGreaterThanOrEqualTo(Object value) {
            addCriterion("conf_item_value >=", value, "confItemValue");
            return (Criteria) this;
        }

        public Criteria andConfItemValueLessThan(Object value) {
            addCriterion("conf_item_value <", value, "confItemValue");
            return (Criteria) this;
        }

        public Criteria andConfItemValueLessThanOrEqualTo(Object value) {
            addCriterion("conf_item_value <=", value, "confItemValue");
            return (Criteria) this;
        }

        public Criteria andConfItemValueIn(List<Object> values) {
            addCriterion("conf_item_value in", values, "confItemValue");
            return (Criteria) this;
        }

        public Criteria andConfItemValueNotIn(List<Object> values) {
            addCriterion("conf_item_value not in", values, "confItemValue");
            return (Criteria) this;
        }

        public Criteria andConfItemValueBetween(Object value1, Object value2) {
            addCriterion("conf_item_value between", value1, value2, "confItemValue");
            return (Criteria) this;
        }

        public Criteria andConfItemValueNotBetween(Object value1, Object value2) {
            addCriterion("conf_item_value not between", value1, value2, "confItemValue");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}