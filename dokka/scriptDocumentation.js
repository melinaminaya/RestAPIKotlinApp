/**
 * Specific changes for client API Documentation.
 * This script must be paste inside clipboard.js
 * @author Melina Minaya
 */
var titleElement = document.querySelector('title');

titleElement.textContent  = "AutotracApi";

var elements = document.getElementsByClassName("library-name--link");

// Change Nav Title
var newText = "Documentação da API de Integração";

for (var i = 0; i < elements.length; i++) {
    elements[i].textContent = newText;
}

//Change H1 title on page
var h1Element = document.querySelector('h1.cover');

if (h1Element) {
  var innerSpanElement = h1Element.querySelector('span > span');

  if (innerSpanElement.textContent === "app") {
    innerSpanElement.textContent = "Index";
  }
}

//Change Breadcrumbs names
var breadcrumbs = document.querySelector(".breadcrumbs");

if (breadcrumbs) {
    var firstLink = breadcrumbs.querySelector("a:first-child");

    if (firstLink) {
        firstLink.textContent = "API";
    }

    var referredLink = findElementWithText(breadcrumbs, 'br.com.autotrac.testnanoclient');

    if (referredLink) {
        var oldText = referredLink.textContent;
        var newText = "";

        if (oldText.includes("br.com.autotrac.testnanoclient.consts")) {
            newText = oldText.replace("br.com.autotrac.testnanoclient.consts", "Constantes");
        } else if (oldText.includes("br.com.autotrac.testnanoclient.dataRemote")) {
            newText = oldText.replace("br.com.autotrac.testnanoclient.dataRemote", "DataClasses");
        } else if (oldText.includes("br.com.autotrac.testnanoclient.di")) {
            newText = oldText.replace("br.com.autotrac.testnanoclient.di", "EndpointGenerico");
        }else if (oldText.includes("br.com.autotrac.testnanoclient.requestObjects")){
            newText = oldText.replace("br.com.autotrac.testnanoclient.requestObjects", "ObjetosRequisições")
        }

        if (newText !== "") {
            referredLink.textContent = newText;
        }
    }
    
}
function findElementWithText(parentElement, searchText) {
    var elements = parentElement.querySelectorAll('*');
    for (var i = 0; i < elements.length; i++) {
      if (elements[i].textContent.includes(searchText)) {
        return elements[i];
      }
    }
    return null;
}

//Remove all unnecessary links
var elementsToRemove = [
    "br.com.autotrac.testnanoclient",
    "br.com.autotrac.testnanoclient.handlers",
    "br.com.autotrac.testnanoclient.common",
    "br.com.autotrac.testnanoclient.navigation", 
    "br.com.autotrac.testnanoclient.screens",
    "br.com.autotrac.testnanoclient.security",
    "br.com.autotrac.testnanoclient.ui.theme",
    "br.com.autotrac.testnanoclient.vm"
];

elementsToRemove.forEach(function(elementText) {
    var linkElement = document.querySelector('div.table a[anchor-label="'+elementText+'"]');
    if (linkElement) {
        linkElement.remove();
    }
    var tableRowElements = document.querySelectorAll('div.table-row');
    
    tableRowElements.forEach(function(tableRowElement) {
        var linkElement = tableRowElement.querySelector('a');
        if (linkElement && linkElement.textContent.trim() === elementText) {
            tableRowElement.remove();
        }
    });
});

//Replace link names
var myMap = new Map();
myMap.set('br.com.autotrac.testnanoclient.consts', 'Constantes');
myMap.set('br.com.autotrac.testnanoclient.dataRemote', 'DataClasses');
myMap.set('br.com.autotrac.testnanoclient.retrofit', 'Endpoint Genérico');
myMap.set('br.com.autotrac.testnanoclient.requestObjects', 'Objetos das Requisições')
myMap.forEach(function(elementText, key){
    var tableRowElements = document.querySelectorAll('div.table-row');
    
    tableRowElements.forEach(function(tableRowElement) {
        var linkElement = tableRowElement.querySelector('a');
        if (linkElement && linkElement.textContent.trim() === key) {
            linkElement.textContent = elementText
        }
    });
});
