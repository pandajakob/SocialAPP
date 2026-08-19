import React from 'react';
import MapView, { Marker } from 'react-native-maps';
import Point from 'react-native-maps';
import { View } from 'react-native';

export default function map() {
  return (
    <View className="flex-1">
    <MapView style={{flex: 1}}>
    <Marker
     coordinate={
        {latitude: 55.6761,
        longitude: 12.5683}}
        pinColor="black"/>
</MapView>
    </View>
  );
}

