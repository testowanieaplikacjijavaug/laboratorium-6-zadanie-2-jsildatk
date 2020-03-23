import org.hamcrest.Matcher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.collection.IsCollectionWithSize.*;
import static org.hamcrest.collection.IsEmptyCollection.*;

public class FriendshipsTestHamcrest {
    
    private Friendships friendships;
    
    @BeforeEach
    public void setUp() {
        friendships = new Friendships();
    }
    
    @Test
    public void shouldThrowExceptionWhenPerson1IsNull() {
        assertThrows(IllegalArgumentException.class, () -> friendships.makeFriends(null, "as"));
    }
    
    @Test
    public void shouldThrowExceptionWhenPerson2IsNull() {
        assertThrows(IllegalArgumentException.class, () -> friendships.makeFriends("asdf", null));
    }
    
    @Test
    public void shouldThrowExceptionWhenPerson1IsBlank() {
        assertThrows(IllegalArgumentException.class, () -> friendships.makeFriends("", "sadf"));
    }
    
    @Test
    public void shouldThrowExceptionWhenPerson2IsBlank() {
        assertThrows(IllegalArgumentException.class, () -> friendships.makeFriends("asdasd", ""));
    }
    
    @Test
    public void shouldThrowExceptionWhenPerson1HasWhitespaces() {
        assertThrows(IllegalArgumentException.class, () -> friendships.makeFriends("      ", "adgffsdgdfg"));
    }
    
    @Test
    public void shouldThrowExceptionWhenPerson2HasWhitespaces() {
        assertThrows(IllegalArgumentException.class, () -> friendships.makeFriends("", "      "));
    }
    
    @Test
    public void shouldMakeFriendsWithPersonsWithNoFriends() {
        final String person1 = "Bartek";
        final String person2 = "Mati";
        
        friendships.makeFriends(person1, person2);
        
        Assertions.assertAll(() -> assertThat(friendships.getFriendsList(person1), hasItem(person2)),
                () -> assertThat(friendships.getFriendsList(person2), hasItem(person1)));
    }
    
    @Test
    public void shouldMakeFriendsWithPersonsWithFriends() {
        final String person1 = "Bartek";
        final String person2 = "Mati";
        final String person3 = "Kuba";
        
        List<String> friends = new ArrayList<>();
        friends.add(person2);
        friendships.friendships.put(person1, friends);
        
        friendships.makeFriends(person1, person3);
        
        Assertions.assertAll(
                () -> assertThat(friendships.getFriendsList(person1), allOf((Matcher<? super List<String>>) hasSize(2), hasItem(person3))),
                () -> assertThat(friendships.getFriendsList(person3), allOf(hasItem(person1))));
    }
    
    @Test
    public void shouldThrowExceptionWhenGettingNullPerson() throws Exception {
        assertThat(exceptionOf(() -> friendships.getFriendsList(null)), instanceOf(IllegalArgumentException.class));
    }
    
    @Test
    public void shouldThrowExceptionWhenGettingBlankPerson() throws Exception {
        assertThat(exceptionOf(() -> friendships.getFriendsList("")), instanceOf(IllegalArgumentException.class));
    }
    
    @Test
    public void shouldThrowExceptionWhenGettingPersonWithWhitespaces() throws Exception {
        assertThat(exceptionOf(() -> friendships.getFriendsList("     ")), instanceOf(IllegalArgumentException.class));
    }
    
    @Test
    public void shouldGetFriendsOfPerson() {
        final String person = "Roman";
        final String friend1 = "Tytus";
        final String friend2 = "Atomek";
        final String friend3 = "Dzusd";
        friendships.friendships.put(person, Arrays.asList(friend1, friend2, friend3));
        
        final List<String> result = friendships.getFriendsList(person);
        assertThat(result, allOf((Matcher<? super List<String>>) hasSize(3), hasItems(friend1, friend2, friend3)));
    }
    
    @Test
    public void shouldGetFriendsOfPersonWithNoFriends() {
        final String person = "Roman";
        final List<String> result = friendships.getFriendsList(person);
        
        assertThat(result, empty());
    }
    
    @Test
    public void shouldReturnTrueWhenCheckingForFriendship() {
        final String person = "Roman";
        final String friend1 = "Tytus";
        
        friendships.friendships.put(person, Collections.singletonList(friend1));
        
        boolean result = friendships.areFriends(person, friend1);
        
        assertThat(result, is(true));
    }
    
    @Test
    public void shouldReturnFalseWhenCheckingForFriendship() {
        final String person = "Roman";
        final String friend1 = "Tytus";
        final String friend2 = "Asad";
        
        friendships.friendships.put(person, Collections.singletonList(friend2));
        
        boolean result = friendships.areFriends(person, friend1);
    
        assertThat(result, is(false));
    }
    
    @AfterEach
    public void tearDown() {
        friendships = null;
    }
    
    private static Throwable exceptionOf(Callable<?> callable) throws Exception {
        try {
            callable.call();
            return null;
        } catch ( Throwable t ) {
            return t;
        }
    }
    
}