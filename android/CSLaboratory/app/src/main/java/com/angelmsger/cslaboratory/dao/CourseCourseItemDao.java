package com.angelmsger.cslaboratory.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.angelmsger.cslaboratory.course.course.CourseCourseItem;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "course_courses".
*/
public class CourseCourseItemDao extends AbstractDao<CourseCourseItem, Long> {

    public static final String TABLENAME = "course_courses";

    /**
     * Properties of entity CourseCourseItem.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property ImageURI = new Property(1, String.class, "imageURI", false, "IMAGE_URI");
        public final static Property Title = new Property(2, String.class, "title", false, "TITLE");
        public final static Property ActionURI = new Property(3, String.class, "actionURI", false, "ACTION_URI");
        public final static Property LearnNum = new Property(4, int.class, "learnNum", false, "LEARN_NUM");
    }


    public CourseCourseItemDao(DaoConfig config) {
        super(config);
    }
    
    public CourseCourseItemDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"course_courses\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"IMAGE_URI\" TEXT NOT NULL ," + // 1: imageURI
                "\"TITLE\" TEXT NOT NULL ," + // 2: title
                "\"ACTION_URI\" TEXT NOT NULL ," + // 3: actionURI
                "\"LEARN_NUM\" INTEGER NOT NULL );"); // 4: learnNum
        // Add Indexes
        db.execSQL("CREATE INDEX " + constraint + "TitleLearnNumIndex ON course_courses" +
                " (\"TITLE\" ASC,\"LEARN_NUM\" DESC);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"course_courses\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CourseCourseItem entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getImageURI());
        stmt.bindString(3, entity.getTitle());
        stmt.bindString(4, entity.getActionURI());
        stmt.bindLong(5, entity.getLearnNum());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CourseCourseItem entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getImageURI());
        stmt.bindString(3, entity.getTitle());
        stmt.bindString(4, entity.getActionURI());
        stmt.bindLong(5, entity.getLearnNum());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public CourseCourseItem readEntity(Cursor cursor, int offset) {
        CourseCourseItem entity = new CourseCourseItem( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // imageURI
            cursor.getString(offset + 2), // title
            cursor.getString(offset + 3), // actionURI
            cursor.getInt(offset + 4) // learnNum
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CourseCourseItem entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setImageURI(cursor.getString(offset + 1));
        entity.setTitle(cursor.getString(offset + 2));
        entity.setActionURI(cursor.getString(offset + 3));
        entity.setLearnNum(cursor.getInt(offset + 4));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(CourseCourseItem entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(CourseCourseItem entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(CourseCourseItem entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
