/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React from 'react';
import {
  StyleSheet,
  ScrollView,
  View,
  Text,
  StatusBar,
} from 'react-native';
import { NavigationContainer, TabActions } from '@react-navigation/native';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import {
  Header,
  LearnMoreLinks,
  Colors,
  DebugInstructions,
  ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';
import Home from './screens/Home';
import MaterialCommunityIcons from 'react-native-vector-icons/MaterialCommunityIcons'

import Search from './screens/Search'
import Ctgeo from './screens/Ctgeo';

const Tab= createBottomTabNavigator()
const App=() =>{
  return (
    <>
      <StatusBar barStyle="dark-content" backgroundColor="#00aaff"/>
      <NavigationContainer>
        <Tab.Navigator
          screenOptions={({route})=>({
            tabBarIcon:({color})=>{
              let iconName;
              if(route.name==="home"){
                iconName='home-city-outline'
              }else if(route.name==="search"){
                iconName="city"
              }
              else if(route.name==="clevertap"){
                iconName="spotlight-beam"
              }
              return <MaterialCommunityIcons name={iconName} size={25} color={color}/>
            }
          })}
          tabBarOptions={{activeTintColor:"white",inactiveTintColor:"gray",activeBackgroundColor:"#00aaff",inactiveBackgroundColor:"#00aaff"}}
        >
          <Tab.Screen name="home" component={Home} initialParams={{city:"london"}}/>
          
          <Tab.Screen name="search" component={Search}/>
          <Tab.Screen name="clevertap" component={Ctgeo}/>
        </Tab.Navigator>
      </NavigationContainer>
    
    </>
  );
};



export default App;
