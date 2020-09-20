package com.example.a5.room.user;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.a5.room.product.Product;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);



    @Query("SELECT * FROM user WHERE uid LIKE :userId LIMIT 1")
    User findById(int userId);

    @Query("SELECT * FROM user WHERE userName LIKE :first AND " +
            "password LIKE :last LIMIT 1")
    User findByEmailPassword(String first, String last);


    @Insert
    void insert(User user);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);



}



//    /**
//     * Get the user from the table. Since for simplicity we only have one user in the database,
//     * this query gets all users from the table, but limits the result to just the 1st user.
//     *
//     * @return the user from the table
//     */
//    @Query("SELECT * FROM Users LIMIT 1")
//    Flowable<User> getUser();
//
//    /**
//     * Insert a user in the database. If the user already exists, replace it.
//     *
//     * @param user the user to be inserted.
//     */
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    Completable insertUser(User user);
//
//    /**
//     * Delete all users.
//     */
//    @Query("DELETE FROM Users")
//    void deleteAllUsers();