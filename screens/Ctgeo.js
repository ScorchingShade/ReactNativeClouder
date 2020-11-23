import * as React from 'react';
import {View, Text, FlatList,Image} from 'react-native';
import { TextInput, Button, Card, Title } from 'react-native-paper';
import Header from './Header'
import {useState,useEffect} from 'react';

const CleverTap = require('clevertap-react-native');

const instructions = Platform.select({
  ios: 'Press Cmd+R to reload,\n' + 'Cmd+D or shake for dev menu',
  android:
    'Double tap R on your keyboard to reload,\n' +
    'Shake or press menu button for dev menu',
});

const Ctgeo = ({name}) => {
    CleverTap.setDebugLevel(3);
    console.log(CleverTap)
   // CleverTap.onUserLogin({'Name': 'testUserA1', 'Identity': '123456', 'Email': 'test@test.com', 'custom1': 123});

    const btnClick=()=>{
    CleverTap.createNotificationChannel("1","1","its 1",3,true);
    CleverTap.onUserLogin({'Name': 'testUserA1', 'Identity': '98989909800', 'Email': 'thisisABigTestAndIwantitToShow@test.com', 'custom1': 123});
    console.log("Check Dashboard!")
    
    
    }

    const updateUserProfile=()=> {
        CleverTap.profileSet({'Name': 'testUserA1', 'Identity': '98989909868', 'Email': 'thisisABigTestAndIwantitToShow@test.com', 'custom1': 123});
        CleverTap.profileSetMultiValuesForKey(['a', 'b', 'c'], 'letters');
        CleverTap.profileAddMultiValueForKey('d', 'letters');
        CleverTap.profileAddMultiValuesForKey(['e', 'f'], 'letters');
        CleverTap.profileRemoveMultiValueForKey('b', 'letters');
        CleverTap.profileRemoveMultiValuesForKey(['a', 'c'], 'letters');
        CleverTap.setLocation(34.15, -118.20);
    }

    useEffect(()=>{
        CleverTap.addListener(CleverTap.CleverTapProfileDidInitialize, (event) => { this._handleCleverTapEvent(CleverTap.CleverTapProfileDidInitialize, event); });
        CleverTap.addListener(CleverTap.CleverTapProfileSync, (event) => { this._handleCleverTapEvent(CleverTap.CleverTapProfileSync, event); });
        CleverTap.addListener(CleverTap.CleverTapInAppNotificationDismissed, (event) => { this._handleCleverTapEvent(CleverTap.CleverTapInAppNotificationDismissed, event); });
        CleverTap.addListener(CleverTap.CleverTapInboxDidInitialize, (event) => { this._handleCleverTapInbox(CleverTap.CleverTapInboxDidInitialize,event); });
        CleverTap.addListener(CleverTap.CleverTapInboxMessagesDidUpdate, (event) => { this._handleCleverTapInbox(CleverTap.CleverTapInboxMessagesDidUpdate,event); });
        CleverTap.addListener(CleverTap.CleverTapInboxMessageButtonTapped, (event) => { this._handleCleverTapInbox(CleverTap.CleverTapInboxMessageButtonTapped,event); });
        CleverTap.addListener(CleverTap.CleverTapDisplayUnitsLoaded, (event) => { this._handleCleverTapDisplayUnitsLoaded(CleverTap.CleverTapDisplayUnitsLoaded,event); });
        CleverTap.addListener(CleverTap.CleverTapInAppNotificationButtonTapped, (event) => { this._handleCleverTapEvent(CleverTap.CleverTapInAppNotificationButtonTapped,event); });
        CleverTap.addListener(CleverTap.CleverTapFeatureFlagsDidUpdate, (event) => { this._handleCleverTapEvent(CleverTap.CleverTapFeatureFlagsDidUpdate,event); });
        CleverTap.addListener(CleverTap.CleverTapProductConfigDidInitialize, (event) => { this._handleCleverTapEvent(CleverTap.CleverTapProductConfigDidInitialize,event); });
        CleverTap.addListener(CleverTap.CleverTapProductConfigDidFetch, (event) => { this._handleCleverTapEvent(CleverTap.CleverTapProductConfigDidFetch,event); });
        CleverTap.addListener(CleverTap.CleverTapProductConfigDidActivate, (event) => { this._handleCleverTapEvent(CleverTap.CleverTapProductConfigDidActivate,event); });
        CleverTap.addListener(CleverTap.CleverTapPushNotificationClicked, (event) => { this._handleCleverTapEvent(CleverTap.CleverTapPushNotificationClicked,event); });

        btnClick();
        updateUserProfile();
    },[])

    return (
     <View>
         <Header name="CleverTap Integration"/>
         
         
             <Text style={{fontSize:23,textAlign:"center"}}> Hello CleverTap</Text>
             
           
             <Button 
                theme={{colors:{primary:"#00aaff"}}} 
                style={{margin:20}}
                icon="cloud" mode="contained" onPress={(text)=>btnClick()}>
                <Text style={{color:"white"}}>Push Profile</Text>
            </Button>

     </View>
    )};
export default Ctgeo;