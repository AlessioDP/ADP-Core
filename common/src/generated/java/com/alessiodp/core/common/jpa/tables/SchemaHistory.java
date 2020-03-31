/*
 * This file is generated by jOOQ.
 */
package com.alessiodp.core.common.jpa.tables;


import com.alessiodp.core.common.jpa.DefaultSchema;
import com.alessiodp.core.common.jpa.Keys;
import com.alessiodp.core.common.jpa.tables.records.SchemaHistoryRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row5;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.13.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SchemaHistory extends TableImpl<SchemaHistoryRecord> {

    private static final long serialVersionUID = -1075364480;

    /**
     * The reference instance of <code>SCHEMA_HISTORY</code>
     */
    public static final SchemaHistory SCHEMA_HISTORY = new SchemaHistory();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SchemaHistoryRecord> getRecordType() {
        return SchemaHistoryRecord.class;
    }

    /**
     * The column <code>SCHEMA_HISTORY.ID</code>.
     */
    public final TableField<SchemaHistoryRecord, Integer> ID = createField(DSL.name("ID"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>SCHEMA_HISTORY.DESCRIPTION</code>.
     */
    public final TableField<SchemaHistoryRecord, String> DESCRIPTION = createField(DSL.name("DESCRIPTION"), org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>SCHEMA_HISTORY.INSTALL_DATE</code>.
     */
    public final TableField<SchemaHistoryRecord, Long> INSTALL_DATE = createField(DSL.name("INSTALL_DATE"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>SCHEMA_HISTORY.SCRIPT_NAME</code>.
     */
    public final TableField<SchemaHistoryRecord, String> SCRIPT_NAME = createField(DSL.name("SCRIPT_NAME"), org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>SCHEMA_HISTORY.VERSION</code>.
     */
    public final TableField<SchemaHistoryRecord, Integer> VERSION = createField(DSL.name("VERSION"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * Create a <code>SCHEMA_HISTORY</code> table reference
     */
    public SchemaHistory() {
        this(DSL.name("SCHEMA_HISTORY"), null);
    }

    /**
     * Create an aliased <code>SCHEMA_HISTORY</code> table reference
     */
    public SchemaHistory(String alias) {
        this(DSL.name(alias), SCHEMA_HISTORY);
    }

    /**
     * Create an aliased <code>SCHEMA_HISTORY</code> table reference
     */
    public SchemaHistory(Name alias) {
        this(alias, SCHEMA_HISTORY);
    }

    private SchemaHistory(Name alias, Table<SchemaHistoryRecord> aliased) {
        this(alias, aliased, null);
    }

    private SchemaHistory(Name alias, Table<SchemaHistoryRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> SchemaHistory(Table<O> child, ForeignKey<O, SchemaHistoryRecord> key) {
        super(child, key, SCHEMA_HISTORY);
    }

    @Override
    public Schema getSchema() {
        return DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public UniqueKey<SchemaHistoryRecord> getPrimaryKey() {
        return Keys.CONSTRAINT_E;
    }

    @Override
    public List<UniqueKey<SchemaHistoryRecord>> getKeys() {
        return Arrays.<UniqueKey<SchemaHistoryRecord>>asList(Keys.CONSTRAINT_E);
    }

    @Override
    public SchemaHistory as(String alias) {
        return new SchemaHistory(DSL.name(alias), this);
    }

    @Override
    public SchemaHistory as(Name alias) {
        return new SchemaHistory(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public SchemaHistory rename(String name) {
        return new SchemaHistory(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public SchemaHistory rename(Name name) {
        return new SchemaHistory(name, null);
    }

    // -------------------------------------------------------------------------
    // Row5 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row5<Integer, String, Long, String, Integer> fieldsRow() {
        return (Row5) super.fieldsRow();
    }
}