import * as React from 'react';
import {View, Text, FlatList} from 'react-native';
import { TextInput, Button, Card } from 'react-native-paper';
import Header from './Header'
import {useState} from 'react';

const Search = ({navigation}) => {
  
    const [city, setcity] = useState('');
    const [cities, setcities] = useState([]);
    const fetchCities=(text)=>{
        setcity(text);
        fetch(`https://api.weather.com/v3/location/search?apiKey=6532d6454b8aa370768e63d6ba5a832e&language=en-US&query=${text}&locationType=city&format=json`)
        .then(item=>item.json())
        .then(cityData=>{
            console.log(cityData.location)
            setcities(cityData.location.address)
        })
    }

    const btnClick=()=>{
        navigation.navigate("home",{city:city})
    }

    const listClick=(cityName)=>{
        setcity(cityName);
        navigation.navigate("home",{city:cityName})
    }

  return (
   <View style={{flex:1}}> 
   <Header name="Search Screen"></Header>
       <TextInput
       label="City Name"
       theme={{colors:{primary:"#00aaff"}}}
       value={city}
       onChangeText={(text)=>fetchCities(text)}
       
       />

    <Button 
        theme={{colors:{primary:"#00aaff"}}} 
        style={{margin:20}}
        icon="content-save" mode="contained" onPress={(text)=>btnClick()}>
        <Text style={{color:"white"}}>Save Changes</Text>
    </Button>

    <FlatList
        data={cities}
        renderItem={({item})=>{
            return(
                <Card
                    style={{margin:2,padding:12}}
                    onPress={()=>listClick(item)}
                
                >
                    <Text>{item}</Text>

                </Card>

            )
        }}
        keyExtractor={item=>item}
    
    />
   </View>
  );
};

export default Search;