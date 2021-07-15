import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISubscripcion, Subscripcion } from 'app/shared/model/subscripcion.model';
import { SubscripcionService } from './subscripcion.service';
import { SubscripcionComponent } from './subscripcion.component';
import { SubscripcionDetailComponent } from './subscripcion-detail.component';
import { SubscripcionUpdateComponent } from './subscripcion-update.component';

@Injectable({ providedIn: 'root' })
export class SubscripcionResolve implements Resolve<ISubscripcion> {
  constructor(private service: SubscripcionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISubscripcion> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((subscripcion: HttpResponse<Subscripcion>) => {
          if (subscripcion.body) {
            return of(subscripcion.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Subscripcion());
  }
}

export const subscripcionRoute: Routes = [
  {
    path: '',
    component: SubscripcionComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'plataformaBackofficeApp.subscripcion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SubscripcionDetailComponent,
    resolve: {
      subscripcion: SubscripcionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'plataformaBackofficeApp.subscripcion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SubscripcionUpdateComponent,
    resolve: {
      subscripcion: SubscripcionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'plataformaBackofficeApp.subscripcion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SubscripcionUpdateComponent,
    resolve: {
      subscripcion: SubscripcionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'plataformaBackofficeApp.subscripcion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
