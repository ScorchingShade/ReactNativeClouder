import * as React from 'react';
import {View, Text, FlatList,Image} from 'react-native';
import { TextInput, Button, Card, Title } from 'react-native-paper';
import Header from './Header'
import {useState,useEffect} from 'react';
import AsyncStorage from '@react-native-community/async-storage'


const Home=(props)=>{

    const [info, setinfo] = useState({
        name:"loading !!",
        temp:"loading !!",
        humidity:"loading",
        desc:"loading",
        icon:"loading"
    });


    const getWeather=async ()=>{
        let MyCity= await AsyncStorage.getItem("newcity");
        if(!MyCity){
            const {city} =props.route.params;
            MyCity=city;
        }

   
        fetch(`https://api.openweathermap.org/data/2.5/weather?q=${MyCity}&appid=0c7ffa0d1d6af905cf8d0d0bfc2db040&units=metric`)
        .then(data=>data.json())
        .then(results=>{
            console.log(results)
            setinfo({
                name:results.name,
                temp:results.main.temp,
                humidity:results.main.humidity,
                desc:results.weather[0].description,
                icon:results.weather[0].icon,
            })
        }).catch((error)=>{
            console.log("Api call error");
            alert(error.message);   
        })
    }

    
    useEffect(()=>{
        getWeather();
    },[])


    if(props.route.params.city!="london"){
        getWeather()
    }

    return(
      <View style={{flex:1}}>
          <Header name="Weather"/>
          <View style={{alignItems:"center"}}>

            <Title
                style={{
                    color:"#00aaff",
                    marginTop:30,
                    fontSize:30
                }}
            >
                {info.name}
            </Title>
            <Image
                style={{
                    width:120,
                    height:120
                }}

                source={{uri:`https://openweathermap.com/img/w/${info.icon}.png`}}
            
            />
          </View>

          <Card style={{
              margin:5,
              padding:12
          }}>

          <Title style={{color:"#00aaff"}}>Temperature - {info.temp}°C </Title>
          </Card>


          <Card style={{
              margin:5,
              padding:12
          }}>

          <Title style={{color:"#00aaff"}}>Humidity - {info.humidity}</Title>
          </Card>


          <Card style={{
              margin:5,
              padding:12
          }}>

          <Title style={{color:"#00aaff"}}>Description - {info.desc}</Title>
          </Card>
      </View>  
    );
}

export default Home