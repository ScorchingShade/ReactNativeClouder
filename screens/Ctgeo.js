import * as React from 'react';
import {View, Text, FlatList,Image} from 'react-native';
import { TextInput, Button, Card, Title } from 'react-native-paper';
import Header from './Header'
import {useState,useEffect} from 'react';
import AsyncStorage from '@react-native-community/async-storage'


const Ctgeo = ({name}) => {
  
    return (
     <View>
         <Header name="CleverTap Integration"/>
         <Text>Hello</Text>
     </View>
    )};
export default Ctgeo;