package hu.bme.mit.spaceship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore mockPTS;
  private TorpedoStore mockSTS;

  @BeforeEach
  public void init(){
    this.mockSTS = mock(TorpedoStore.class);
    this.mockPTS = mock(TorpedoStore.class);
    this.ship = new GT4500(mockPTS, mockSTS);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(mockPTS.fire(1)).thenReturn(true);
    when(mockSTS.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockPTS, times(1)).fire(1);
    verify(mockSTS, times(0)).fire(1);
    assertTrue(result);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(mockPTS.fire(1)).thenReturn(true);
    when(mockPTS.getTorpedoCount()).thenReturn(10);
    when(mockSTS.fire(1)).thenReturn(true);
    when(mockSTS.getTorpedoCount()).thenReturn(10);


    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockPTS, times(10)).fire(1);
    verify(mockSTS, times(10)).fire(1);
    assertTrue(result);
  }

  @Test
  public void fireTorpedo_Single_PrimaryIsEmpty() {
    when(mockPTS.fire(1)).thenReturn(false);
    when(mockPTS.isEmpty()).thenReturn(true);
    when(mockSTS.fire(1)).thenReturn(true);
    when(mockSTS.isEmpty()).thenReturn(false);

    assertTrue(ship.fireTorpedo(FiringMode.SINGLE));

    verify(mockPTS, times(0)).fire(1);
    verify(mockSTS, times(1)).fire(1);
  }

  @Test
  void fireTorpedo_ThreeTimes() {
    when(mockPTS.fire(1)).thenReturn(true);
    when(mockPTS.isEmpty()).thenReturn(false);
    when(mockSTS.fire(1)).thenReturn(true);
    when(mockSTS.isEmpty()).thenReturn(false);

    assertTrue(ship.fireTorpedo(FiringMode.SINGLE));
    verify(mockPTS, times(1)).fire(1);
    verify(mockSTS, times(0)).fire(1);

    assertTrue(ship.fireTorpedo(FiringMode.SINGLE));
    verify(mockPTS, times(1)).fire(1);
    verify(mockSTS, times(1)).fire(1);

    assertTrue(ship.fireTorpedo(FiringMode.SINGLE));
    verify(mockPTS, times(2)).fire(1);
    verify(mockSTS, times(1)).fire(1);

    //second is Empty
    when(mockSTS.fire(1)).thenReturn(false);
    when(mockSTS.isEmpty()).thenReturn(true);

    assertTrue(ship.fireTorpedo(FiringMode.SINGLE));
    verify(mockPTS, times(3)).fire(1);
    verify(mockSTS, times(1)).fire(1);
  }

  @Test
  void fireTorpedo_ALL_whenEmpty() {
    when(mockPTS.getTorpedoCount()).thenReturn(0);
    when(mockPTS.isEmpty()).thenReturn(true);
    when(mockSTS.getTorpedoCount()).thenReturn(0);
    when(mockSTS.isEmpty()).thenReturn(true);

    assertFalse(ship.fireTorpedo(FiringMode.ALL));
    verify(mockPTS, times(0)).fire(1);
    verify(mockSTS, times(0)).fire(1);
  }

  @Test
  void fireLaser_Single() {
    assertTrue(ship.fireLaser(FiringMode.SINGLE));
  }

}
