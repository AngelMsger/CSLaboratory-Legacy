package com.angelmsger.cslaboratory.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.angelmsger.cslaboratory.quality.category.QualityCategoryItem;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "quality_categories".
*/
public class QualityCategoryItemDao extends AbstractDao<QualityCategoryItem, Long> {

    public static final String TABLENAME = "quality_categories";

    /**
     * Properties of entity QualityCategoryItem.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Title = new Property(1, String.class, "title", false, "TITLE");
        public final static Property ImageURI = new Property(2, String.class, "imageURI", false, "IMAGE_URI");
    }


    public QualityCategoryItemDao(DaoConfig config) {
        super(config);
    }
    
    public QualityCategoryItemDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"quality_categories\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"TITLE\" TEXT NOT NULL UNIQUE ," + // 1: title
                "\"IMAGE_URI\" TEXT NOT NULL );"); // 2: imageURI
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"quality_categories\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, QualityCategoryItem entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getTitle());
        stmt.bindString(3, entity.getImageURI());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, QualityCategoryItem entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getTitle());
        stmt.bindString(3, entity.getImageURI());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public QualityCategoryItem readEntity(Cursor cursor, int offset) {
        QualityCategoryItem entity = new QualityCategoryItem( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // title
            cursor.getString(offset + 2) // imageURI
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, QualityCategoryItem entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTitle(cursor.getString(offset + 1));
        entity.setImageURI(cursor.getString(offset + 2));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(QualityCategoryItem entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(QualityCategoryItem entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(QualityCategoryItem entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
