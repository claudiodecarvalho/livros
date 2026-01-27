import { bootstrapApplication } from '@angular/platform-browser';
import { registerLocaleData } from '@angular/common';
import localePt from '@angular/common/locales/pt';
import { appConfig } from './app/app.config';
import { App } from './app/app';

// Registra dados de locale para pt/pt-BR
registerLocaleData(localePt);

bootstrapApplication(App, appConfig)
  .catch((err) => console.error(err));
