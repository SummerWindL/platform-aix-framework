package com.platform.aix.common.datacommon.db.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PrescriptionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PrescriptionExample() {
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

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andIngredientsIsNull() {
            addCriterion("ingredients is null");
            return (Criteria) this;
        }

        public Criteria andIngredientsIsNotNull() {
            addCriterion("ingredients is not null");
            return (Criteria) this;
        }

        public Criteria andIngredientsEqualTo(String value) {
            addCriterion("ingredients =", value, "ingredients");
            return (Criteria) this;
        }

        public Criteria andIngredientsNotEqualTo(String value) {
            addCriterion("ingredients <>", value, "ingredients");
            return (Criteria) this;
        }

        public Criteria andIngredientsGreaterThan(String value) {
            addCriterion("ingredients >", value, "ingredients");
            return (Criteria) this;
        }

        public Criteria andIngredientsGreaterThanOrEqualTo(String value) {
            addCriterion("ingredients >=", value, "ingredients");
            return (Criteria) this;
        }

        public Criteria andIngredientsLessThan(String value) {
            addCriterion("ingredients <", value, "ingredients");
            return (Criteria) this;
        }

        public Criteria andIngredientsLessThanOrEqualTo(String value) {
            addCriterion("ingredients <=", value, "ingredients");
            return (Criteria) this;
        }

        public Criteria andIngredientsLike(String value) {
            addCriterion("ingredients like", value, "ingredients");
            return (Criteria) this;
        }

        public Criteria andIngredientsNotLike(String value) {
            addCriterion("ingredients not like", value, "ingredients");
            return (Criteria) this;
        }

        public Criteria andIngredientsIn(List<String> values) {
            addCriterion("ingredients in", values, "ingredients");
            return (Criteria) this;
        }

        public Criteria andIngredientsNotIn(List<String> values) {
            addCriterion("ingredients not in", values, "ingredients");
            return (Criteria) this;
        }

        public Criteria andIngredientsBetween(String value1, String value2) {
            addCriterion("ingredients between", value1, value2, "ingredients");
            return (Criteria) this;
        }

        public Criteria andIngredientsNotBetween(String value1, String value2) {
            addCriterion("ingredients not between", value1, value2, "ingredients");
            return (Criteria) this;
        }

        public Criteria andUsageIsNull() {
            addCriterion("usage is null");
            return (Criteria) this;
        }

        public Criteria andUsageIsNotNull() {
            addCriterion("usage is not null");
            return (Criteria) this;
        }

        public Criteria andUsageEqualTo(String value) {
            addCriterion("usage =", value, "usage");
            return (Criteria) this;
        }

        public Criteria andUsageNotEqualTo(String value) {
            addCriterion("usage <>", value, "usage");
            return (Criteria) this;
        }

        public Criteria andUsageGreaterThan(String value) {
            addCriterion("usage >", value, "usage");
            return (Criteria) this;
        }

        public Criteria andUsageGreaterThanOrEqualTo(String value) {
            addCriterion("usage >=", value, "usage");
            return (Criteria) this;
        }

        public Criteria andUsageLessThan(String value) {
            addCriterion("usage <", value, "usage");
            return (Criteria) this;
        }

        public Criteria andUsageLessThanOrEqualTo(String value) {
            addCriterion("usage <=", value, "usage");
            return (Criteria) this;
        }

        public Criteria andUsageLike(String value) {
            addCriterion("usage like", value, "usage");
            return (Criteria) this;
        }

        public Criteria andUsageNotLike(String value) {
            addCriterion("usage not like", value, "usage");
            return (Criteria) this;
        }

        public Criteria andUsageIn(List<String> values) {
            addCriterion("usage in", values, "usage");
            return (Criteria) this;
        }

        public Criteria andUsageNotIn(List<String> values) {
            addCriterion("usage not in", values, "usage");
            return (Criteria) this;
        }

        public Criteria andUsageBetween(String value1, String value2) {
            addCriterion("usage between", value1, value2, "usage");
            return (Criteria) this;
        }

        public Criteria andUsageNotBetween(String value1, String value2) {
            addCriterion("usage not between", value1, value2, "usage");
            return (Criteria) this;
        }

        public Criteria andIndicationsIsNull() {
            addCriterion("indications is null");
            return (Criteria) this;
        }

        public Criteria andIndicationsIsNotNull() {
            addCriterion("indications is not null");
            return (Criteria) this;
        }

        public Criteria andIndicationsEqualTo(String value) {
            addCriterion("indications =", value, "indications");
            return (Criteria) this;
        }

        public Criteria andIndicationsNotEqualTo(String value) {
            addCriterion("indications <>", value, "indications");
            return (Criteria) this;
        }

        public Criteria andIndicationsGreaterThan(String value) {
            addCriterion("indications >", value, "indications");
            return (Criteria) this;
        }

        public Criteria andIndicationsGreaterThanOrEqualTo(String value) {
            addCriterion("indications >=", value, "indications");
            return (Criteria) this;
        }

        public Criteria andIndicationsLessThan(String value) {
            addCriterion("indications <", value, "indications");
            return (Criteria) this;
        }

        public Criteria andIndicationsLessThanOrEqualTo(String value) {
            addCriterion("indications <=", value, "indications");
            return (Criteria) this;
        }

        public Criteria andIndicationsLike(String value) {
            addCriterion("indications like", value, "indications");
            return (Criteria) this;
        }

        public Criteria andIndicationsNotLike(String value) {
            addCriterion("indications not like", value, "indications");
            return (Criteria) this;
        }

        public Criteria andIndicationsIn(List<String> values) {
            addCriterion("indications in", values, "indications");
            return (Criteria) this;
        }

        public Criteria andIndicationsNotIn(List<String> values) {
            addCriterion("indications not in", values, "indications");
            return (Criteria) this;
        }

        public Criteria andIndicationsBetween(String value1, String value2) {
            addCriterion("indications between", value1, value2, "indications");
            return (Criteria) this;
        }

        public Criteria andIndicationsNotBetween(String value1, String value2) {
            addCriterion("indications not between", value1, value2, "indications");
            return (Criteria) this;
        }

        public Criteria andFilePathIsNull() {
            addCriterion("file_path is null");
            return (Criteria) this;
        }

        public Criteria andFilePathIsNotNull() {
            addCriterion("file_path is not null");
            return (Criteria) this;
        }

        public Criteria andFilePathEqualTo(String value) {
            addCriterion("file_path =", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotEqualTo(String value) {
            addCriterion("file_path <>", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathGreaterThan(String value) {
            addCriterion("file_path >", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathGreaterThanOrEqualTo(String value) {
            addCriterion("file_path >=", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathLessThan(String value) {
            addCriterion("file_path <", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathLessThanOrEqualTo(String value) {
            addCriterion("file_path <=", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathLike(String value) {
            addCriterion("file_path like", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotLike(String value) {
            addCriterion("file_path not like", value, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathIn(List<String> values) {
            addCriterion("file_path in", values, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotIn(List<String> values) {
            addCriterion("file_path not in", values, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathBetween(String value1, String value2) {
            addCriterion("file_path between", value1, value2, "filePath");
            return (Criteria) this;
        }

        public Criteria andFilePathNotBetween(String value1, String value2) {
            addCriterion("file_path not between", value1, value2, "filePath");
            return (Criteria) this;
        }

        public Criteria andCreatedAtIsNull() {
            addCriterion("created_at is null");
            return (Criteria) this;
        }

        public Criteria andCreatedAtIsNotNull() {
            addCriterion("created_at is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedAtEqualTo(Date value) {
            addCriterion("created_at =", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtNotEqualTo(Date value) {
            addCriterion("created_at <>", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtGreaterThan(Date value) {
            addCriterion("created_at >", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtGreaterThanOrEqualTo(Date value) {
            addCriterion("created_at >=", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtLessThan(Date value) {
            addCriterion("created_at <", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtLessThanOrEqualTo(Date value) {
            addCriterion("created_at <=", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtIn(List<Date> values) {
            addCriterion("created_at in", values, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtNotIn(List<Date> values) {
            addCriterion("created_at not in", values, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtBetween(Date value1, Date value2) {
            addCriterion("created_at between", value1, value2, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtNotBetween(Date value1, Date value2) {
            addCriterion("created_at not between", value1, value2, "createdAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtIsNull() {
            addCriterion("updated_at is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtIsNotNull() {
            addCriterion("updated_at is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtEqualTo(Date value) {
            addCriterion("updated_at =", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtNotEqualTo(Date value) {
            addCriterion("updated_at <>", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtGreaterThan(Date value) {
            addCriterion("updated_at >", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtGreaterThanOrEqualTo(Date value) {
            addCriterion("updated_at >=", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtLessThan(Date value) {
            addCriterion("updated_at <", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtLessThanOrEqualTo(Date value) {
            addCriterion("updated_at <=", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtIn(List<Date> values) {
            addCriterion("updated_at in", values, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtNotIn(List<Date> values) {
            addCriterion("updated_at not in", values, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtBetween(Date value1, Date value2) {
            addCriterion("updated_at between", value1, value2, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtNotBetween(Date value1, Date value2) {
            addCriterion("updated_at not between", value1, value2, "updatedAt");
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