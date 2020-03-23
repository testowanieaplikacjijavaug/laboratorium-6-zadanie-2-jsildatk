import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class FriendshipsTestAssertJ {
    
    private Friendships friendships;
    
    @BeforeEach
    public void setUp() {
        friendships = new Friendships();
    }
    
    @Test
    public void shouldThrowExceptionWhenPerson1IsNull() {
        assertThatThrownBy(() -> friendships.makeFriends(null, "as")).isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    public void shouldThrowExceptionWhenPerson2IsNull() {
        assertThatThrownBy(() -> friendships.makeFriends("asdf", null)).isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    public void shouldThrowExceptionWhenPerson1IsBlank() {
        assertThatThrownBy(() -> friendships.makeFriends("", "asdf")).isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    public void shouldThrowExceptionWhenPerson2IsBlank() {
        assertThatThrownBy(() -> friendships.makeFriends("sdgdsfg", "")).isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    public void shouldThrowExceptionWhenPerson1HasWhitespaces() {
        assertThatThrownBy(() -> friendships.makeFriends("      ", "asdasdasd")).isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    public void shouldThrowExceptionWhenPerson2HasWhitespaces() {
        assertThatThrownBy(() -> friendships.makeFriends("", "      ")).isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    public void shouldMakeFriendsWithPersonsWithNoFriends() {
        final String person1 = "Bartek";
        final String person2 = "Mati";
        
        friendships.makeFriends(person1, person2);
        
        Assertions.assertAll(() -> assertThat(friendships.getFriendsList(person1)).contains(person2),
                () -> assertThat(friendships.getFriendsList(person2)).contains(person1));
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
        
        Assertions.assertAll(() -> assertThat(friendships.getFriendsList(person1)).hasSize(2)
                        .contains(person3),
                () -> assertThat(friendships.getFriendsList(person3)).contains(person1));
    }
    
    @Test
    public void shouldThrowExceptionWhenGettingNullPerson() {
        assertThatThrownBy(() -> friendships.getFriendsList(null)).isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    public void shouldThrowExceptionWhenGettingBlankPerson() {
        assertThatThrownBy(() -> friendships.getFriendsList("")).isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    public void shouldThrowExceptionWhenGettingPersonWithWhitespaces() {
        assertThatThrownBy(() -> friendships.getFriendsList("       ")).isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    public void shouldGetFriendsOfPerson() {
        final String person = "Roman";
        final String friend1 = "Tytus";
        final String friend2 = "Atomek";
        final String friend3 = "Dzusd";
        friendships.friendships.put(person, Arrays.asList(friend1, friend2, friend3));
        
        final List<String> result = friendships.getFriendsList(person);
        
        assertThat(result).hasSize(3)
                .contains(friend1, friend2, friend3);
    }
    
    @Test
    public void shouldGetFriendsOfPersonWithNoFriends() {
        final String person = "Roman";
        final List<String> result = friendships.getFriendsList(person);
        
        assertThat(result).isEmpty();
    }
    
    @Test
    public void shouldReturnTrueWhenCheckingForFriendship() {
        final String person = "Roman";
        final String friend1 = "Tytus";
        
        friendships.friendships.put(person, Collections.singletonList(friend1));
        
        boolean result = friendships.areFriends(person, friend1);
        
        assertThat(result).isTrue();
    }
    
    @Test
    public void shouldReturnFalseWhenCheckingForFriendship() {
        final String person = "Roman";
        final String friend1 = "Tytus";
        final String friend2 = "Asad";
        
        friendships.friendships.put(person, Collections.singletonList(friend2));
        
        boolean result = friendships.areFriends(person, friend1);
        
        assertThat(result).isFalse();
    }
    
    @AfterEach
    public void tearDown() {
        friendships = null;
    }
    
}