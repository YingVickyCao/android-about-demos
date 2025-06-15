package com.example.kotlin.test.db;

import androidx.room.RenameTable;
import androidx.room.migration.AutoMigrationSpec;

@RenameTable(fromTableName = "menu", toTableName = "menu2")
//@RenameTable.Entries(@RenameTable(fromTableName = "menu", toTableName = "menu2"))
public class L2ToL3MigrationSpec implements AutoMigrationSpec {

}
