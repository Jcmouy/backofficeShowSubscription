import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFuncion, Funcion } from 'app/shared/model/funcion.model';
import { FuncionService } from './funcion.service';
import { FuncionComponent } from './funcion.component';
import { FuncionDetailComponent } from './funcion-detail.component';
import { FuncionUpdateComponent } from './funcion-update.component';

@Injectable({ providedIn: 'root' })
export class FuncionResolve implements Resolve<IFuncion> {
  constructor(private service: FuncionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFuncion> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((funcion: HttpResponse<Funcion>) => {
          if (funcion.body) {
            return of(funcion.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Funcion());
  }
}

export const funcionRoute: Routes = [
  {
    path: '',
    component: FuncionComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'plataformaBackofficeApp.funcion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FuncionDetailComponent,
    resolve: {
      funcion: FuncionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'plataformaBackofficeApp.funcion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FuncionUpdateComponent,
    resolve: {
      funcion: FuncionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'plataformaBackofficeApp.funcion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FuncionUpdateComponent,
    resolve: {
      funcion: FuncionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'plataformaBackofficeApp.funcion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
